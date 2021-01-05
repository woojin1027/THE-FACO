#include <stdio.h>

int main() {
	char a;

	printf("문자입력 : ");
	scanf_s("%c", &a);


	printf("당신이 입력한 문자는 %c 입니다\n", a);
	printf("당신이 입력한 숫자는(ASCII 의 값) %d 입니다\n", a);
}