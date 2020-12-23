#include <stdio.h>

int main() {
	float a, b;

	scanf_s("%f%f\n", &a, &b);

	float sum = a + b;
	float cha = a - b;

	printf("합은 : %f , 차는 : %f\n", sum, cha);
}