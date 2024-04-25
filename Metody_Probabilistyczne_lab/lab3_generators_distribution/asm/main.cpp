#include <time.h>
#include <random>
#include <iostream>
#include <vector>
using namespace std;
#define N  100000
#define LEFT 50
#define RIGHT 150
#define RANGE RIGHT - LEFT
#define INTERVAL 10


extern "C" { float quantile_function(float number); }

void print_ranges(int* counts) {
	for (int i = 0; i < 10; i++) {
		cout<< (RANGE / INTERVAL) * i + LEFT<<" - " << (RANGE / INTERVAL) * (i + 1) + LEFT << ": " << counts[i] << endl;
	}
}

int main()
{
	int counts[INTERVAL];
	for (int i = 0; i < INTERVAL; i++) {
		counts[i] = 0;
	}

	random_device rd;
	default_random_engine eng(rd());
	uniform_real_distribution<float> distr(0, 1);
	
	for (int i = 0; i < N; i++) {
		float a = distr(eng);
		float result = a * 100 + 50;
		//float result = quantile_function(a);
		int index = INTERVAL * (result - 50) / 100;
		counts[index] ++;
	}
	print_ranges(counts);

	//cout << a << endl;
	return 0;
}