//Name: Jason Yang
//ID Number: 1614276
#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <pthread.h>
#include <x86intrin.h>
#include <unistd.h>
#include <limits.h>
#include <time.h>
#include <string.h>

#define CACHE_LINE_SIZE 64  // Size of a cache line in bytes
#define LLC_SIZE (36 * 1024 * 1024)  // Estimated size of the last-level cache (LLC) in bytes (e.g., 36MB)
#define ARRAY_SIZE (LLC_SIZE * 3)  // Size of the array to be allocated, three times the LLC size
#define NUM_ACCESSES (ARRAY_SIZE / CACHE_LINE_SIZE)  // Number of cache lines in the allocated array
#define NUM_ITERATIONS 20  // Number of iterations to average the detected LLC size
#define NUM_MEASUREMENTS 1000  // Number of measurements per cache access
#define THRESHOLD_PERCENTILE 0.95  // Percentile to determine the threshold for a cache miss
#define NUM_THREADS 4  // Number of threads used for the experiment

// Structure to hold data for each thread
typedef struct {
    char *array;
    uint64_t *timings;
    size_t start;
    size_t end;
} ThreadData;

// Function to measure the access time of a given memory address
uint64_t measure_access_time(void *addr) {
    uint64_t start, end;
    start = __rdtsc();  // Read the timestamp counter before access
    volatile uint8_t value = *((volatile uint8_t *)addr);  // Access the memory address
    end = __rdtsc();  // Read the timestamp counter after access
    return end - start;  // Return the difference (access time)
}

// Function to prime the cache by accessing each cache line
void prime_cache(char *array) {
    for (size_t i = 0; i < NUM_ACCESSES; i++) {
        array[i * CACHE_LINE_SIZE] = 1;  // Access each cache line to load it into the cache
    }
}

// Thread function to probe the cache and measure access times
void* probe_cache(void* arg) {
    ThreadData *data = (ThreadData*) arg;
    for (size_t i = data->start; i < data->end; i++) {
        uint64_t total_time = 0;
        for (int j = 0; j < NUM_MEASUREMENTS; j++) {
            total_time += measure_access_time(&data->array[(rand() % NUM_ACCESSES) * CACHE_LINE_SIZE]);
        }
        data->timings[i] = total_time / NUM_MEASUREMENTS;  // Average access time for each cache line
    }
    return NULL;
}

// Function to calculate and print statistics of the measured access times
void print_statistics(uint64_t *timings) {
    uint64_t total_time = 0;
    uint64_t max_time = 0;
    uint64_t min_time = UINT64_MAX;

    for (size_t i = 0; i < NUM_ACCESSES; i++) {
        total_time += timings[i];
        if (timings[i] > max_time) {
            max_time = timings[i];
        }
        if (timings[i] < min_time) {
            min_time = timings[i];
        }
    }

    double average_time = total_time / (double)NUM_ACCESSES;

    printf("Access Time Statistics:\n");
    printf("Average: %.2f cycles\n", average_time);
    printf("Max: %lu cycles\n", max_time);
    printf("Min: %lu cycles\n", min_time);
}

// Function to detect the size of the LLC based on access times
size_t detect_llc_size(uint64_t *timings) {
    size_t cache_lines = 0;

    // Allocate memory to sort the timings
    uint64_t *sorted_timings = (uint64_t *)malloc(NUM_ACCESSES * sizeof(uint64_t));
    if (sorted_timings == NULL) {
        perror("malloc");
        exit(EXIT_FAILURE);
    }

    // Copy and sort the timings
    memcpy(sorted_timings, timings, NUM_ACCESSES * sizeof(uint64_t));
    qsort(sorted_timings, NUM_ACCESSES, sizeof(uint64_t), (int (*)(const void *, const void *))memcmp);

    // Determine the threshold as the 95th percentile
    uint64_t threshold = sorted_timings[(size_t)(NUM_ACCESSES * THRESHOLD_PERCENTILE)];

    // Count the number of cache lines with access times below the threshold
    for (size_t i = 0; i < NUM_ACCESSES; i++) {
        if (timings[i] < threshold) {
            cache_lines++;
        }
    }

    free(sorted_timings);

    // Return the estimated LLC size
    return cache_lines * CACHE_LINE_SIZE;
}

int main() {
    // Seed the random number generator
    srand(time(NULL));

    // Allocate a large array to test LLC size
    char *array = (char *)malloc(ARRAY_SIZE);
    if (array == NULL) {
        perror("malloc");
        exit(EXIT_FAILURE);
    }

    // Allocate memory for timing measurements
    uint64_t *timings = (uint64_t *)malloc(NUM_ACCESSES * sizeof(uint64_t));
    if (timings == NULL) {
        perror("malloc");
        free(array);
        exit(EXIT_FAILURE);
    }

    // Array to hold the detected LLC sizes in each iteration
    size_t detected_llc_sizes[NUM_ITERATIONS];

    pthread_t threads[NUM_THREADS];  // Array to hold thread IDs
    ThreadData thread_data[NUM_THREADS];  // Array to hold data for each thread
    size_t chunk_size = NUM_ACCESSES / NUM_THREADS;  // Number of cache lines per thread

    // Loop through the specified number of iterations
    for (int iter = 0; iter < NUM_ITERATIONS; iter++) {
        // Prime the cache
        prime_cache(array);

        // Create threads to probe the cache and measure access times
        for (int t = 0; t < NUM_THREADS; t++) {
            thread_data[t].array = array;
            thread_data[t].timings = timings;
            thread_data[t].start = t * chunk_size;
            thread_data[t].end = (t == NUM_THREADS - 1) ? NUM_ACCESSES : (t + 1) * chunk_size;
            pthread_create(&threads[t], NULL, probe_cache, &thread_data[t]);
        }

        // Wait for all threads to finish
        for (int t = 0; t < NUM_THREADS; t++) {
            pthread_join(threads[t], NULL);
        }

        // Print the statistics of the measured times
        printf("Iteration %d:\n", iter + 1);
        print_statistics(timings);

        // Detect LLC size
        detected_llc_sizes[iter] = detect_llc_size(timings);
        printf("Detected LLC Size in iteration %d: %zu bytes\n", iter + 1, detected_llc_sizes[iter]);
    }

    // Calculate the average detected LLC size
    size_t total_detected_size = 0;
    for (int iter = 0; iter < NUM_ITERATIONS; iter++) {
        total_detected_size += detected_llc_sizes[iter];
    }
    size_t average_llc_size = total_detected_size / NUM_ITERATIONS;
    printf("\nAverage Detected LLC Size: %zu bytes\n", average_llc_size);

    // Free allocated memory
    free(timings);
    free(array);

    return 0;
}
