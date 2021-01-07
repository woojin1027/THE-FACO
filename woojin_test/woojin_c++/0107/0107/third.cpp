// 포인터

#include <stdio.h>

int main() {
	int a = 20;

	int *ptr_a = &a;
	// &a : a 의 주소값

	printf("ptr_a에 저장된 값 %d\n", ptr_a);

	printf("ptr_a가 가리키는 변수 : %d\n", *ptr_a);

}