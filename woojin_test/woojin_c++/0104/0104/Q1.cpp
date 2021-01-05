// 두 숫자의 입력을 받아서 그 숫자들의 합을 출력하는 프로그램을 구성하라

#include <stdio.h>

int main() {
	float a, b;
	printf("두 숫자를 입력하세요\n");
	scanf_s("%f%f", &a, &b);
	float c = a + b;
	printf("\n두 숫자의 합은 %f 입니다.", c);

}