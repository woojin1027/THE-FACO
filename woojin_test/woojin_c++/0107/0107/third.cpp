// ������

#include <stdio.h>

int main() {
	int a = 20;

	int *ptr_a = &a;
	// &a : a �� �ּҰ�

	printf("ptr_a�� ����� �� %d\n", ptr_a);

	printf("ptr_a�� ����Ű�� ���� : %d\n", *ptr_a);

}