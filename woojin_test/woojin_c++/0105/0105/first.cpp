// 산술연산자 이용

#include <stdio.h>

int main() {

	int a = 5;
	printf("a는 원래 %d 였습니다. \n", a);

	a = a + 3;
	printf("3을 더했더니 a의 값은 %d 가 되었습니다.\n", a);

	a += 5;
	printf("거기에 5를 더하면 그 값은 %d가 되었습니다.", a);

}