#include <stdio.h>

int main() {
	int a = 3;
	int b = 5.12;
	double c = 2;

	int hap = a + b;

	printf("%d + %d = %d\n", a, b, hap);

	printf("%d\n", sizeof(char));
	printf("%d\n", sizeof(double));
	printf("%f\n", c);
	printf("%d\n", b);
}


//sizeof(x) : x의 크기를 알려준다.(바이트 단위)