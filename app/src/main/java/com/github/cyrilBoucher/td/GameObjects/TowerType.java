package com.github.cyrilBoucher.td.GameObjects;

public enum TowerType {
	Basic {
		@Override
		public int getCost() {
			return 100;
		}

		@Override
		public int getUpgradeCost() {
			return 75;
		}

		@Override
		public int getPower() {
			return 20;
		}

		@Override
		public int getFiringRange() {
			return 1;
		}
	},
	Scout {
		@Override
		public int getCost() {
			return 120;
		}

		@Override
		public int getUpgradeCost() {
			return 90;
		}

		@Override
		public int getPower() {
			return 15;
		}

		@Override
		public int getFiringRange() {
			return 3;
		}
	},
	Blaster {
		@Override
		public int getCost() {
			return 200;
		}

		@Override
		public int getUpgradeCost() {
			return 150;
		}

		@Override
		public int getPower() {
			return 30;
		}

		@Override
		public int getFiringRange() {			
			return 2;
		}
	};
	
	public abstract int getCost();
	public abstract int getUpgradeCost();
	public abstract int getPower();
	public abstract int getFiringRange();

}
