

#include <stdlib.h>
#include <omp.h>



void aggregate(float* lhs, float* rhs, int totalpoints){
int i;
#pragma omp parallel for
	for (i = 0; i < totalpoints; i++) {
float rhsVal;

		rhsVal = rhs[i];
		lhs[i] += rhsVal;
	}
}



int main () {
	int N = 364000;
	float* lhs = (float*)malloc(N * sizeof(float));
	float* rhs = (float*)malloc(N * sizeof(float));


int i;
	for(i=0; i<10000; ++i) {
		aggregate(lhs, rhs, N);
	}


	return 0;
}

