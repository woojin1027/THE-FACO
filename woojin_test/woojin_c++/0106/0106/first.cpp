#include <stdio.h>

int main() {

	int choice;
	makechoice:
	printf("������ : 1\n");
	printf("�ҷ����� : 2\n");
	printf("���� : 3\n");
	printf("ũ���� : 4\n");

	scanf_s("%d", &choice);

	switch (choice) {
	case1:
		printf("�� ����\n");
		break;
	case2:
		printf("�ҷ�����\n");
		break;
	case3:
		printf("����\n");
		break;
	case4:
		printf("ũ����\n");
		break;
	default:
		printf("�߸� ����\n");
		goto makechoice;
		// �� goto�� �� ������� ���ƿ�. ������ ��������°ſ���.
		break;
	}
}