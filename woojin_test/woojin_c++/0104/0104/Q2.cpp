//  체중과 키를 입력 받앙서 체질량 지수를 구하는 프로그램을 만들보자.

#include <stdio.h>

int main() {
	float w, h;

	printf("체중(kg 단위) : ");
	scanf_s("%f", &w);

	printf("키(m 단위) : ");
	scanf_s("%f", &h);

	float bmi = w / (h * h);
	printf(" 체질량 지수(BMI)는 %f 입니다", bmi);
}