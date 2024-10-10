# Mario Game 소개
  ## 마리오 게임을 구현
  1. USER: 사용자가 직접 방향키를 통해 캐릭터를 움직이며, 몬스터를 피해 공주를 구하는 게임
  2. COM: 앞서 txt파일에 저장된 경로를 입력받아, 저장된 경로대로 캐릭터가 따라감. 만약 저장된 경로가 없다면 캐릭터가 랜덤으로 움직임.
  3. Learning: 랜덤으로 생성되는 값을 통해 캐릭터가 랜덤으로 움직이며, 최종적으로 공주를 구할 경우 경로를 txt파일에 저장시킴. 만약 이전에 저장된 경로가 더 효율적이라면 저장시키지 않음.
  ![image](https://github.com/kimjy0117/marioGame/assets/113746577/0a9201fc-a3aa-4bd4-ad41-37963b0a8bd0)
  <br><br>

# USER모드
## 1단계
걸어다니는 몬스터 생성
![image](https://github.com/kimjy0117/marioGame/assets/113746577/02899274-4078-4b5c-ba33-369b3b75ead1)
<br><br>
아이템을 먹을 경우 생명력 1증가
![image](https://github.com/kimjy0117/marioGame/assets/113746577/a4440bd0-0d05-44ed-a13a-eed3e7df1e9f)
![image](https://github.com/kimjy0117/marioGame/assets/113746577/379b04ed-4baf-40dc-ac64-a32e85273352)
<br><br>

## 2단계
플레이어를 따라다니는 플라잉 몬스터 추가
![image](https://github.com/kimjy0117/marioGame/assets/113746577/43dba831-d1fd-4494-9bf8-3b00d9ea0460)
<br><br>

## 3단계
플레이어를 따라다니며 점프하는 몬스터 추가
![image](https://github.com/kimjy0117/marioGame/assets/113746577/8e12911a-f3ee-4560-af78-9b02026b5b35)
<br><br>

## 결과창
플레이 시간, 플레이어 죽은 횟수를 통해 5위까지 순위를 산출
플레이어 NAME은 "Guest"뒤에 숫자를 1씩 증가시켜 생성
만약 자신의 순위가 5위 안에 들어갈 경우 빨간색으로 강조
![image](https://github.com/kimjy0117/marioGame/assets/113746577/ebb26915-2a6e-4388-bd64-7a74d039c043)
<br><br>

# COM모드
## 1단계
앞서 저장되어 있던 경로로 몬스터를 피해 다음 단계로 진입
![image](https://github.com/kimjy0117/marioGame/assets/113746577/b7ac6740-35a2-4294-b14e-b28c55c68c19)
<br><br>

## 2단계
1단계와 마찬가지로 저장되어 있던 경로로 몬스터를 피해 공주를 구출
![image](https://github.com/kimjy0117/marioGame/assets/113746577/308c8caa-4c9d-4bc7-bba9-6f11abdc5cde)
<br><br>

## 결과창
플레이 시간, 플레이어 죽은 횟수를 통해 5위까지 순위를 산출
플레이어 NAME은 "Com"뒤에 숫자를 1씩 증가시켜 생성
USER모드와 독립적으로 순위를 산출
![image](https://github.com/kimjy0117/marioGame/assets/113746577/2deeb0e5-3a07-45b4-9294-f5f3a0d1fd03)
<br><br>

# Learning모드
1. 랜덤으로 값을 생성시켜, 캐릭터가 랜덤으로 생성된 값을 통해 움직임
2. 특정 구간에서 캐릭터가 죽을 경우 특정 구간 이전에 체크포인트를 만들어 그 지점까지는 이전과 동일하게 움직이며, 체크포인트 이후에는 다시 랜덤값을 부여받아 움직임
3. 2단계에서 공주를 구출할 경우, 시작지점부터 공주를 구출한 시점까지의 시간을 산출
4. 이전에 저장되어있던 경로보다 효율적이면 해당 경로를 저장하고, 효율적이지 않으면 해당 경로를 저장시키지 않음
5. 데드락에 걸릴 경우를 대비하여 캐릭터가 일정 횟수 이상 죽을 경우 해당 경로를 삭제 후 처음부터 다시 랜덤 값을 부여받음
6. 위의 과정을 계속 반복하여 제일 최단시간의 경로를 구함
![image](https://github.com/kimjy0117/marioGame/assets/113746577/64ae7cc8-f8ed-44fb-ae31-728bb99c2d54)

