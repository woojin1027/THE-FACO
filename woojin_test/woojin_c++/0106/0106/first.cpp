#include <stdio.h>

int main() {

	int choice;
	makechoice:
	printf("새게임 : 1\n");
	printf("불러오기 : 2\n");
	printf("설정 : 3\n");
	printf("크레딧 : 4\n");

	scanf_s("%d", &choice);

	switch (choice) {
	case1:
		printf("새 게임\n");
		break;
	case2:
		printf("불러오기\n");
		break;
	case3:
		printf("설정\n");
		break;
	case4:
		printf("크레딧\n");
		break;
	default:
		printf("잘못 기입\n");
		goto makechoice;
		// 단 goto는 잘 사용하지 말아요. 귀찮고 어려워지는거에요.
		break;
	}
}