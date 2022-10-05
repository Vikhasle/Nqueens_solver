import java.util.ArrayList;

class Queen{
    int x;
    int y;

    Queen(int x, int y){
        this.x = x;
        this.y = y;
    }
}

class Queens{
    static boolean[][] board;

    static void print_board(){
        for (int i = 0; i < board.length; i++) {
            System.out.println("-----------------------------------");
            for (int j = 0; j < board.length; j++) {
                System.out.printf(" | ");
                if(board[i][j])
                    System.out.printf("Q");
                else
                    System.out.printf(" ");
            }
            System.out.println(" | ");
        }
        System.out.println("-----------------------------------");
    }

    static void prune(ArrayList<Queen> queens){
        for(int i = 0; i < board.length; i++)
            for(int j = 0; j < board.length; j++)
                board[i][j] = true;
        for (Queen q : queens) {
            for(int i = 0; i < board.length; i++){
                board[i][q.x] = false;
                board[q.y][i] = false;
                if(q.y - i >= 0 && q.x + i < board.length)
                    board[q.y-i][q.x+i] = false;
                if(q.y - i >= 0 && q.x - i >= 0)
                    board[q.y-i][q.x-i] = false;
                if(q.x - i >= 0 && q.y + i < board.length)
                    board[q.y+i][q.x-i] = false;
                if(q.y + i < board.length && q.x + i < board.length)
                    board[q.y+i][q.x+i] = false;
            }
        }
    }

    static ArrayList<Queen> copy(ArrayList<Queen> list){
        ArrayList<Queen> c = new ArrayList<Queen>();
        for (Queen q : list)
            c.add(q);
        return c;
    }

    static boolean eqs(ArrayList<Queen> lq1, ArrayList<Queen> lq2){
        if(lq1.size() != lq2.size())
            return false;
        for (int i = 0; i < lq1.size(); i++) {
            boolean found = false;
            for(int j = 0; j < lq2.size(); j++)
                if(lq1.get(i).x == lq2.get(i).x || lq1.get(i).y == lq2.get(i).y){
                    found = true;
                    break;
                }
            if(!found)
                return false;
        }
        return true;
    }

    static void solve(int n){
        int k = 0;
        board = new boolean[n][n];
        ArrayList<Queen> queens = new ArrayList<Queen>();
        ArrayList<ArrayList<Queen>> bad_pos = new ArrayList<ArrayList<Queen>>();
        prune(queens);
        while(k < n){
            boolean found = false;
            for (int i = 0; i < board.length && !found;  i++){
                for(int j = 0; j < board.length && !found; j++)
                    if(board[i][j]){
                        queens.add(new Queen(j,i));
                        found = true;
                        k++;
                        for (ArrayList<Queen> arrayList : bad_pos) {
                            if(eqs(queens, arrayList)){
                                queens.remove(queens.size() - 1);
                                k--;
                                found = false;
                            }
                        }
                    }
            }
            if(!found){
                bad_pos.add(copy(queens));
                queens.remove(queens.size() - 1);
                k--;
            }
            prune(queens);
        }
        for (Queen q : queens)
            board[q.y][q.x] = true;
    }

    public static void main(String[] args) {
        solve(8);
        print_board();
    }
}
