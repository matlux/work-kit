#include <pthread.h>
#include <stdio.h>

#define NUM_THREADS	13

void *thread_func(void *threadid)
{
	double d = 1.0;
	int i;
	for(i=0; i<1000000000; ++i)
		d += d / (d + 1.0);

	*(int*)threadid = (int)d;
	pthread_exit(NULL);
}

int main(void)
{
	pthread_t threads[NUM_THREADS];
	int res[NUM_THREADS];
	int t;

	for(t=0;t<NUM_THREADS;t++){
		printf("creating thread %ld\n", t);
		int rc = pthread_create(&threads[t], NULL, thread_func, (void *)&res[t]);
		if (rc){
			printf("ERROR; return code from pthread_create() is %d\n", rc);
			return -1;
		}
	}
	for(t=0;t<NUM_THREADS;t++){	
		pthread_join(threads[t], NULL);
		printf("thread %ld terminated\n", t);
	}	

	pthread_exit(NULL);

	return 0;
}

