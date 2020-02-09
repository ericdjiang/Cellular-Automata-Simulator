package cellsociety.cells;

public class FireCell extends Cell{

        private int burnTimeLeft;

        public FireCell(int state, int burnTime) {
            super(state);

            burnTimeLeft = burnTime;
        }

        @Override
        public void updateState(){
            if(state==2) {
                burnTimeLeft--;
            }
            state=nextState;
        }

        public boolean burntOut() {return burnTimeLeft<=0;}


        public void burn(int burnTime) {
            nextState = 2;
            burnTimeLeft=burnTime;
        }


    }


