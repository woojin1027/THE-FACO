// ��ø if ��
// �߰�ȣ �ڵ� ��Ÿ��

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
	// �̷��� ����� ��ȣ ���� �ʴ� ����̴�.
	// �߰�ȣ�� �ִ��� ������ �������.



}