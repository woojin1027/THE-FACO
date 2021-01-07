#include <stdio.h>

int main() {

	int n;
	printf("숫자를 입력하세요 : ");
	scanf_s("%d", &n);

	for (int i = 0, t = 1; i <= n; i++ ,t*= 2) {
		printf("2^%d = %d\n",i,t);
	}
	for (int i = 1, sum = 0; i <= n; sum += i, i++) {
		printf("%d\n", sum);
	}
}