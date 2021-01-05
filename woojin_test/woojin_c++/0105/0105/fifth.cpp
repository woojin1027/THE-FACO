// 중첩 if 문
// 중괄호 코딩 스타일

#include <stdio.h>

int main() {

	int a, b, c;

	scanf_s("%d%d%d", &a, &b, &c);

	if (a > b) {
		if (b > c) {
			printf("%d\n", a);
		}
		else {
			printf("%d\n", c);
			}
	}
	else {
		if (b > c) {
			printf("%d\n", b);
		}
		else {
			printf("%d\n", c);
		}
	}
	// 이러한 방법은 선호 되지 않는 방법이다.
	// 중괄호를 최대한 빼도록 노력하자.



}