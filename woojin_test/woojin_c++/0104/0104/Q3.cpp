// 알파벳을 입력 받아서 그 다음 알파벳을 출력하는 프로그램을 만들어보라

#include <stdio.h>

int main() {
	char a;
	printf("알파벳을 입력하세요 : ");
	scanf_s("%c", &a);

	char b = a + 1;
	printf("입력받은 알파벳의 다음 알파벳을 %c 입니다\n", b);
}