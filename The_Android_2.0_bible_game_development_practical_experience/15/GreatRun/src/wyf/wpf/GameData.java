package wyf.wpf;		//声明包语句

public class GameData{
	public static int [][][][] mapData={
		{//第1关关卡数据，存放是图片的ID+1,1:路，2：草地，3：木桩，4：树，5，花
			{//下层平铺层
				{3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
				{3,1,1,1,1,1,1,2,1,1,1,1,1,1,3},
				{3,1,2,2,2,2,1,1,1,2,2,2,2,1,3},
				{3,1,1,1,2,1,1,2,1,1,2,1,1,1,3},
				{3,1,2,1,2,1,2,2,2,1,2,1,2,1,3},
				{3,1,2,1,1,1,1,2,1,1,1,1,2,1,3},
				{3,1,2,2,1,2,1,1,1,2,1,2,2,1,3},
				{3,1,1,1,1,2,1,2,1,2,1,1,1,1,3},
				{3,1,2,2,2,2,1,2,1,2,2,2,2,1,3},
				{3,1,1,1,1,2,1,2,1,2,1,1,1,1,3},
				{3,3,3,3,1,2,1,2,1,2,1,3,3,3,3},
				{5,5,5,3,1,1,1,1,1,1,1,3,5,5,5},
				{5,5,5,3,1,2,2,2,2,2,1,3,5,5,5},
				{5,5,5,3,1,1,1,2,1,1,1,3,5,5,5},
				{3,3,3,3,1,2,1,2,1,2,1,3,3,3,3},
				{3,1,1,1,1,2,1,2,1,2,1,1,1,1,3},
				{3,1,2,2,1,2,1,1,1,2,1,2,2,1,3},
				{3,1,2,1,1,1,1,2,1,1,1,1,2,1,3},
				{3,1,2,1,2,2,2,2,2,2,2,1,2,1,3},
				{3,1,2,1,1,1,1,1,1,1,1,1,2,1,3},
				{3,1,2,1,2,2,2,2,2,2,2,1,2,1,3},
				{3,1,1,1,1,1,1,2,1,1,1,1,1,1,3},
				{3,1,2,2,2,2,1,2,1,2,2,2,2,1,3},
				{3,1,1,1,1,1,1,1,1,1,1,1,1,1,3},
				{3,3,3,3,3,3,3,3,3,3,3,3,3,3,3}
			},
			{//上层平铺层
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,4,0,0,0,0,0,0,0},
				{0,0,4,0,0,0,4,4,4,0,0,0,4,0,0},
				{0,0,4,0,0,0,0,4,0,0,0,0,4,0,0},
				{0,0,4,4,0,0,0,0,0,0,0,4,4,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,4,4,4,4,4,0,0,0,0,0},
				{0,0,0,0,0,0,0,4,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,4,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,4,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,4,4,4,0,4,4,4,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,4,4,4,4,0,0,0,4,4,4,4,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
			}
		},
		{//第2关地图数据，存放的是图片的ID+1,1:路，2：草地，3：木桩，4：树，5，花
			{//下层平铺层
				{3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
				{3,1,1,1,1,3,1,1,1,3,1,1,1,1,3},
				{3,1,5,5,1,3,1,3,1,3,1,5,5,1,3},
				{3,1,5,5,1,1,1,1,1,1,1,5,5,1,3},
				{3,1,1,1,1,2,1,2,1,2,1,1,1,1,3},
				{3,3,3,3,1,2,2,2,2,2,1,3,3,3,3},
				{3,1,1,1,1,1,1,1,1,1,1,1,1,1,3},
				{3,1,3,3,3,3,3,1,3,3,3,3,3,1,3},
				{3,1,1,3,1,1,1,1,1,1,1,3,1,1,3},
				{3,3,1,3,1,5,5,1,5,5,1,3,1,3,3},
				{3,3,1,1,1,5,5,1,5,5,1,1,1,3,3},
				{5,3,1,3,1,5,5,1,5,5,1,3,1,3,5},
				{5,1,1,3,1,1,1,1,1,1,1,3,1,1,5},
				{5,1,3,3,3,3,3,1,3,3,3,3,3,1,5},
				{3,1,1,1,1,1,1,1,1,1,1,1,1,1,3},
				{3,3,3,1,3,1,3,3,3,1,3,1,3,3,3},
				{3,3,3,1,3,1,1,3,1,1,3,1,3,3,3},
				{3,3,3,1,3,3,1,3,1,3,3,1,3,3,3},
				{3,1,1,1,1,1,1,3,1,1,1,1,1,1,3},
				{3,3,1,3,1,2,1,3,1,2,1,3,1,3,3},
				{3,1,1,3,1,1,1,1,1,1,1,3,1,1,3},
				{3,3,1,3,3,3,3,3,3,3,3,3,1,3,3},
				{3,3,1,3,1,1,1,1,1,1,1,3,1,3,3},
				{3,1,1,1,1,3,3,3,3,3,1,1,1,1,3},
				{3,3,3,3,3,3,3,3,3,3,3,3,3,3,3}
			},
			{//上层平铺层
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,4,0,4,0,4,0,0,0,0,0},
				{0,0,0,0,0,4,4,4,4,4,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,4,0,0,0,4,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
			}
		}
	};
	public static int [][][][] notInData ={	//不可通过矩阵，0代表可通过，1代表不可通过
		{//第1关不可通过情况
			{//下层平铺层不可通过矩阵
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,0,0,0,0,0,0,1,0,0,0,0,0,0,1},
				{1,0,1,1,1,1,0,0,0,1,1,1,1,0,1},
				{1,0,0,0,1,0,0,1,0,0,1,0,0,0,1},
				{1,0,1,0,1,0,1,1,1,0,1,0,1,0,1},
				{1,0,1,0,0,0,0,1,0,0,0,0,1,0,1},
				{1,0,1,1,0,1,0,0,0,1,0,1,1,0,1},
				{1,0,0,0,0,1,0,1,0,1,0,0,0,0,1},
				{1,0,1,1,1,1,0,1,0,1,1,1,1,0,1},
				{1,0,0,0,0,1,0,1,0,1,0,0,0,0,1},
				{1,1,1,1,0,1,0,1,0,1,0,1,1,1,1},
				{1,1,1,1,0,0,0,0,0,0,0,1,1,1,1},
				{1,1,1,1,0,1,1,1,1,1,0,1,1,1,1},
				{1,1,1,1,0,0,0,1,0,0,0,1,1,1,1},
				{1,1,1,1,0,1,0,1,0,1,0,1,1,1,1},
				{1,0,0,0,0,1,0,1,0,1,0,0,0,0,1},
				{1,0,1,1,0,1,0,0,0,1,0,1,1,0,1},
				{1,0,1,0,0,0,0,1,0,0,0,0,1,0,1},
				{1,0,1,0,1,1,1,1,1,1,1,0,1,0,1},
				{1,0,1,0,0,0,0,0,0,0,0,0,1,0,1},
				{1,0,1,0,1,1,1,1,1,1,1,0,1,0,1},
				{1,0,0,0,0,0,0,1,0,0,0,0,0,0,1},
				{1,0,1,1,1,1,0,1,0,1,1,1,1,0,1},
				{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
			},
			{//上层平铺层不可通过矩阵
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
				{0,0,1,0,0,0,1,1,1,0,0,0,1,0,0},
				{0,0,1,0,0,0,0,1,0,0,0,0,1,0,0},
				{0,0,1,1,0,0,0,0,0,0,0,1,1,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,1,1,1,1,1,0,0,0,0,0},
				{0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,1,1,1,0,1,1,1,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,1,1,1,1,0,0,0,1,1,1,1,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
			}
		},
		{//第2关不可通过情况
			{//下层平铺层不可通过矩阵
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
				{1,0,1,1,0,1,0,1,0,1,0,1,1,0,1},
				{1,0,1,1,0,0,0,0,0,0,0,1,1,0,1},
				{1,0,0,0,0,1,0,1,0,1,0,0,0,0,1},
				{1,1,1,1,0,1,1,1,1,1,0,1,1,1,1},
				{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,0,1,1,1,1,1,0,1,1,1,1,1,0,1},
				{1,0,0,1,0,0,0,0,0,0,0,1,0,0,1},
				{1,1,0,1,0,1,1,0,1,1,0,1,0,1,1},
				{1,1,0,0,0,1,1,0,1,1,0,0,0,1,1},
				{1,1,0,1,0,1,1,0,1,1,0,1,0,1,1},
				{1,0,0,1,0,0,0,0,0,0,0,1,0,0,1},
				{1,0,1,1,1,1,1,0,1,1,1,1,1,0,1},
				{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,1,1,0,1,0,1,1,1,0,1,0,1,1,1},
				{1,1,1,0,1,0,0,1,0,0,1,0,1,1,1},
				{1,1,1,0,1,1,0,1,0,1,1,0,1,1,1},
				{1,0,0,0,0,0,0,1,0,0,0,0,0,0,1},
				{1,1,0,1,0,1,0,1,0,1,0,1,0,1,1},
				{1,0,0,1,0,0,0,0,0,0,0,1,0,0,1},
				{1,1,0,1,1,1,1,1,1,1,1,1,0,1,1},
				{1,1,0,1,0,0,0,0,0,0,0,1,0,1,1},
				{1,0,0,0,0,1,1,1,1,1,0,0,0,0,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
			},
			{//上层平铺层不可通过矩阵
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,1,0,1,0,1,0,0,0,0,0},
				{0,0,0,0,0,1,1,1,1,1,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,1,0,0,0,1,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
			}
		}
	};
	//每个关卡英雄的位置---列在前，行在后
	public static int [][] heroLocations={
			{1,2},{2,8}
	};
	//每个关卡家的位置---列在前，行在后
	public static int [][] homeLocations={
			{6,1},{10,12}
	};
	//每个关卡怪物的数量
	public static int [] monsterNumbers={1,2};
	//每个关卡怪物的位置--列在前，行在后
	public static int [][][] monsterLocations={
			{{8,8}},
			{{13,3},{2,22}}
	};	
	//静态方法，获取英雄位置
	public static int[] getHeroLocationByStage(int stage){
		return heroLocations[stage];
	}
	//静态方法，获取家的位置
	public static int[] getHomeLocationByStage(int stage){
		return homeLocations[stage];
	}
	//静态方法，获取怪物数量
	public static int getMonsterNumberByStage(int stage){
		return monsterNumbers[stage];
	}
	//静态方法，获取怪物位置
	public static int[][] getMonsterLocationByStage(int stage){
		return monsterLocations[stage];		
	}
}