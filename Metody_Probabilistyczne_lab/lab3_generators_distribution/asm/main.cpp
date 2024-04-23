#include <time.h>
#include <random>
#include <iostream>
using namespace std;

extern "C" { float quantile_function(float number); }

int main()
{
	srand((unsigned int)(time(0)));
	cout << time(NULL) << endl;

	float a = ((float)rand()) / RAND_MAX;
	cout << a << endl;

	float result = quantile_function(a);

	cout << result << endl;
}