// if �� 

#include <stdio.h>

int main() {

	int a;
	scanf_s("%d", &a);
	// �� :1 , ���� :0
	if (a == 0) {
		printf("a�� 0 �̴�.\n");
	}
	else if (a % 2 == 0) {
		printf("a�� ¦�� �̴�.\n");
	}
	else {
		printf("a�� Ȧ���̴�.\n");
	}
}