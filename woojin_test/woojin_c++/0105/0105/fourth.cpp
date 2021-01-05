// if 문 

#include <stdio.h>

int main() {

	int a;
	scanf_s("%d", &a);
	// 참 :1 , 거짓 :0
	if (a == 0) {
		printf("a은 0 이다.\n");
	}
	else if (a % 2 == 0) {
		printf("a은 짝수 이다.\n");
	}
	else {
		printf("a은 홀수이다.\n");
	}
}