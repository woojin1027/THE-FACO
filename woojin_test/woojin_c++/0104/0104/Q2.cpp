//  ü�߰� Ű�� �Է� �޾Ӽ� ü���� ������ ���ϴ� ���α׷��� ���麸��.

#include <stdio.h>

int main() {
	float w, h;

	printf("ü��(kg ����) : ");
	scanf_s("%f", &w);

	printf("Ű(m ����) : ");
	scanf_s("%f", &h);

	float bmi = w / (h * h);
	printf(" ü���� ����(BMI)�� %f �Դϴ�", bmi);
}