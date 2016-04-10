package com.example.lam.chesscaroandroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class Board_HumVsCom extends View {
    private int m = 40, n = 40; // Khởi tạo số ô cờ
    public Boolean firstPlayerX = true;// X đánh
    private static int empty_cell = 0;
    private static int x_cell = 1;
    private static int o_cell = -1;
    int maxi = 0;
    int maxj = 0;

    private int grid_width = 25;
    private int grid_height = 25;
    private int grid_size = 40;
    public int playerTurn = 0;// Thằng đầu tiên đánh

    int mX, mY;// Tọa độ X,Y khi chạm tay
    // Cài đặt bắt sự kiện cho việc cài đặt bên Obj_Setting


    // Cấp phát động vùng nhớ cho amrng 2 chiều
    private int[][] arr = new int[m][n];

    public Board_HumVsCom(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Board_HumVsCom(Context context) {
        super(context);
        System.err.println("onCreate Obj_MultiBoardChess");
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                // 3 Xóa trắng các ô cờ
                this.arr[i][j] = empty_cell;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        DisplayMetrics displayMetric = new DisplayMetrics();// Hàm này là hàm hiển thị số liệu của thiết bị
        String cell_char = new String();
        System.err.println("call to Chessboard onDraw MultiBoardChess");
        super.onDraw(canvas);

        // Lấy kích thước của màn hình
        System.err.println("get window size MultiBoardChess");
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetric);
        //((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetric);
        System.err.println("done get window size MultiBoardChess");
        int width = displayMetric.widthPixels;// Hiển thị số liệu độ rộng trên màn hình
        int height = displayMetric.heightPixels;// Hiển thị số liệu độ cao màn hình

        // Tính kích thước ô cờ
        // Kích thước thiết bị
        // Chia cho số ô cờ để tính được kích thước ô cờ
        // số ô cờ là m

        if(height/m < width/n){
            grid_size = width/n;
        }
        // Màn hình máy nhỏ
        else if(width/n > height/m){
            grid_size = height/m;

        }
        System.out.println("Tính: "+"m= "+m+"; n= "+n);
        n = width/ (width/n);
        m = height/ (grid_size) ;
        System.out.println("Giá trị sau vẽ : "+"m= "+m+"; n= "+n+"grid_sze"+grid_size);
        grid_width = n;
        grid_height =m;
        System.out.println("Giá trị sau vẽ : "+"grid_height= "+grid_height+"; grid_width= "+grid_width+";grid_sze"+grid_size);

        System.err.println("new paint MultiBoardChess");
        Paint paint = new Paint();
        paint.setStrokeWidth(2); // Độ đậm nhạt của đường kẻ và quân đánh
        for (int i = 0; i < grid_width; i++) {
            for (int j = 0; j < grid_height; j++) {
                if (arr[i][j] == empty_cell) {
                    paint.setColor(Color.WHITE);// set màu của ô cờ trống
                    cell_char = " ";
                }
                else if (arr[i][j] == x_cell) {
                    paint.setColor(Color.RED);// set màu của ô cờ x
                    cell_char = "X";
                }
                else if (arr[i][j] == o_cell) {
                    paint.setColor(Color.BLUE);// set màu của ô cờ O
                    cell_char = "O";
                }
//                System.err.println("draw one cell of the board MultiBoardChess");
                //Fill rectangle
                paint.setTextSize(grid_size / 2);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawRect(new Rect(i * grid_size, j * grid_size, (i + 1) * grid_size, (j + 1) * grid_size), paint);

                // Border rectangle
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(Color.BLACK);
                canvas.drawRect(new Rect(i * grid_size, j * grid_size, (i + 1) * grid_size, (j + 1) * grid_size), paint);
                Rect bounds= new Rect();
                paint.getTextBounds("X", 0, 1, bounds);
                canvas.drawText(cell_char,0,1, i*grid_size + (grid_size-bounds.width())/2, j*grid_size+ (bounds.height()+(grid_size-bounds.height())/2), paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Gọi thằng đi trước từ Obj_Setting
        firstPlayerX= Obj_Settings.getFirstPlayerX(getContext());
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // tọa độ X của điểm chạm
                mX = (int) event.getX();// Tọa độ khik2 đánh ở X
                // tọa độ Y của điểm chạm
                mY = (int) event.getY();// Tọa độ khi đánh ở Y
                System.out.println("Action down " + mX + "  " + mY + " " + (int) mX / grid_size + " " + (int) mY / grid_size);

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                ///////////////////////(playTurn ==0)///////////////////////////////
                // Lượt chơi của Human
                if (playerTurn == 0) {// Lượt đi của người đầu tiên
                    System.out.println("Playturn = 0" + " Người đánh");
                    if (this.arr[(int) mX / grid_size][(int) mY / grid_size] == empty_cell) {
                        // Lượt đi của thằng Human nếu đánh quân "X"
                        if (firstPlayerX == true) {
                            this.arr[(int) mX / grid_size][(int) mY / grid_size] = x_cell; // đổi trạng thái ô cờ
                        }
                        // Lượt đi của thằng Human nếu đánh quân "O"
                        if (firstPlayerX == false) {
                            this.arr[(int) mX / grid_size][(int) mY / grid_size] = o_cell; // đổi trạng thái ô cờ
                        }
//                        this.invalidate();// Hàm này để vẽ lại quân cờ đã chọn
                        //System.out.println("Vẽ lại bàn cờ khi thằng Human đánh");
                        // Chuyển lượt đi cho Computer
                        playerTurn = 1;
                        int hanghuman = mX/grid_size;
                        int cothuman = mY/grid_size;
                        int demhuman =1;
                        int index=1;
                        while (hanghuman + index < m && arr[hanghuman][cothuman]==arr[hanghuman+index][cothuman]){
                            demhuman++;
                            index++;
                        }index=1; // reset chỉ số
                        while (hanghuman - index >0 && arr[hanghuman][cothuman]==arr[hanghuman-index][cothuman]){
                            demhuman++;
                            index++;
                        }if(demhuman >= 5){
                            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Cờ caro");
                            builder.setIcon(R.mipmap.congra);
                            builder.setMessage("Người đã chiến thắng máy");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.create().show();
                        }

                        demhuman=1;
                        index=1;
                        while (cothuman + index < n && arr[hanghuman][cothuman]==arr[hanghuman][cothuman+index]){
                            demhuman++;
                            index++;
                        }index=1; // reset chỉ số
                        while (cothuman - index >0 && arr[hanghuman][cothuman]==arr[hanghuman][cothuman- index]){
                            demhuman++;
                            index++;
                        }if(demhuman >= 5){
                            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Cờ caro");
                            builder.setIcon(R.mipmap.congra);
                            builder.setMessage("Người đã chiến thắng máy");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.create().show();
                        }

                        demhuman=1;
                        index=1;
                        while(hanghuman + index < m && cothuman + index <n && arr[hanghuman][cothuman]==arr[hanghuman+ index][cothuman+index]){
                            demhuman++;
                            index++;
                        }index=1;
                        while(cothuman - index >0 && hanghuman - index > 0 && arr[hanghuman][cothuman]==arr[hanghuman- index][cothuman- index]) {
                            demhuman++;
                            index++;
                        }if(demhuman >= 5){
                            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Cờ caro");
                            builder.setIcon(R.mipmap.congra);
                            builder.setMessage("Người đã chiến thắng máy");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.create().show();
                        }

                        demhuman=1;
                        index=1;
                        while(hanghuman + index < m && cothuman - index > 0 && arr[hanghuman][cothuman]==arr[hanghuman+ index][cothuman-index]){
                            demhuman++;
                            index++;
                        }index=1;
                        while(cothuman + index < n && hanghuman - index > 0 && arr[hanghuman][cothuman]==arr[hanghuman- index][cothuman+ index]) {
                            demhuman++;
                            index++;
                        }if(demhuman >= 5){
                            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Cờ caro");
                            builder.setIcon(R.mipmap.congra);
                            builder.setMessage("Người đã chiến thắng máy");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.create().show();
                        }

                        this.invalidate();// Hàm này để vẽ lại quân cờ đã chọn

                        if(demhuman >= 5){
                            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Cờ caro");
                            builder.setIcon(R.mipmap.congra);
                            builder.setMessage("Người đã chiến thắng máy");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.create().show();
                        }
                    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////playTurn==1/////////////////////////////////
                    // Lượt đi của Computer
                    // playTurn != 0 là thằng máy đánh
                    if (playerTurn == 1) {
                        System.out.println("Playturn = 1" + " Máy đánh");
                        // Khởi tạo mảng điểm chặn
                        int ArrayPointBlock[][] = new int[m][n];
                        //System.out.println("Khởi tạo điểm chặn");
                        // Duyệt mảng từ đầu đến cuối
                        for (int i = 0; i < arr.length; i++) {
                            for (int j = 0; j < arr[0].length; j++) {
                                int hang = (int) i;
                                int cot = (int) j;
                                int index =1;
                                int dem_x_cell=1;
                                int dem_o_cell=1;

                                // Tọa độ computer x_cell đánh arr[hang][cot] có thể viết arr[(int) i][(int) j]

                                if (arr[hang][cot] == empty_cell) {// Kiểm tra ô cờ trống
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                    ////////////// firstPlayerX == false////////////////
                                    // Máy chọn o_cell
                                    if (firstPlayerX == false) {
                                        // Máy đánh OOOOOOOOO_cell, người đánh XXXXXXXXXXX_cell(người đánh x_cell ở trên)

                                        dem_x_cell =1;
                                        index =1;
                                        // Kiểm tra hàng ngang
                                        while (hang + index < m && arr[hang+index][cot]== x_cell){
                                            dem_x_cell++;
                                            index++;
                                        }
                                        index=1;
                                        while (hang - index >0 && arr[hang-index][cot]== x_cell){
                                            dem_x_cell++;
                                            index++;
                                        }

                                        switch (dem_x_cell) {
                                            case 5: if (ArrayPointBlock[i][j] < 10)
                                                ArrayPointBlock[i][j] = 10;
                                                break;
                                            case 4: if (ArrayPointBlock[i][j] < 8)
                                                ArrayPointBlock[i][j] = 8;
                                                break;
                                            case 3: if (ArrayPointBlock[i][j] < 6)
                                                ArrayPointBlock[i][j] = 6;
                                                break;
                                            case 2: if (ArrayPointBlock[i][j] < 4)
                                                ArrayPointBlock[i][j] = 4;
                                                break;
                                        }

                                        // Kiểm tra cột dọc
                                        dem_x_cell=1;
                                        index=1;
                                        while (cot + index < n && arr[hang][cot+index]== x_cell){
                                            dem_x_cell++;
                                            index++;
                                        }
                                        index=1;
                                        while (cot - index >0 && arr[hang][cot- index]== x_cell) {
                                            dem_x_cell++;
                                            index++;
                                        }
                                        switch (dem_x_cell) {
                                            case 5: if (ArrayPointBlock[i][j] < 10)
                                                ArrayPointBlock[i][j] = 10;
                                                break;
                                            case 4: if (ArrayPointBlock[i][j] < 8)
                                                ArrayPointBlock[i][j] = 8;
                                                break;
                                            case 3: if (ArrayPointBlock[i][j] < 6)
                                                ArrayPointBlock[i][j] = 6;
                                                break;
                                            case 2: if (ArrayPointBlock[i][j] < 4)
                                                ArrayPointBlock[i][j] = 4;
                                                break;
                                        }

                                        // Kiểm tra chéo trái
                                        dem_x_cell=1;
                                        index=1;
                                        while (hang + index < m && cot + index <n && arr[hang+ index][cot+index]== x_cell){
                                            dem_x_cell++;
                                            index++;
                                        }
                                        index=1;
                                        while (cot - index >0 && hang - index > 0 && arr[hang- index][cot- index]== x_cell) {
                                            dem_x_cell++;
                                            index++;
                                        }
                                        switch (dem_x_cell) {
                                            case 5: if (ArrayPointBlock[i][j] < 10)
                                                ArrayPointBlock[i][j] = 10;
                                                break;
                                            case 4: if (ArrayPointBlock[i][j] < 8)
                                                ArrayPointBlock[i][j] = 8;
                                                break;
                                            case 3: if (ArrayPointBlock[i][j] < 6)
                                                ArrayPointBlock[i][j] = 6;
                                                break;
                                            case 2: if (ArrayPointBlock[i][j] < 4)
                                                ArrayPointBlock[i][j] = 4;
                                                break;
                                        }

                                        // Kiểm tra chéo phải
                                        dem_x_cell=1;
                                        index=1;
                                        while (hang + index < m && cot - index > 0 && arr[hang+ index][cot-index]== x_cell){
                                            dem_x_cell++;
                                            index++;
                                        }
                                        index=1;
                                        while (cot + index < n && hang - index > 0 && arr[hang- index][cot+ index]== x_cell){
                                            dem_x_cell++;
                                            index++;
                                        }
                                        switch (dem_x_cell) {
                                            case 5: if (ArrayPointBlock[i][j] < 10)
                                                ArrayPointBlock[i][j] = 10;
                                                break;
                                            case 4: if (ArrayPointBlock[i][j] < 8)
                                                ArrayPointBlock[i][j] = 8;
                                                break;
                                            case 3: if (ArrayPointBlock[i][j] < 6)
                                                ArrayPointBlock[i][j] = 6;
                                                break;
                                            case 2: if (ArrayPointBlock[i][j] < 4)
                                                ArrayPointBlock[i][j] = 4;
                                                break;
                                        }

//                                        // Thằng người đánh x_cell so sánh vs o_cell của thằng máy đánh
                                        // Kiểm tra hàng ngang
                                        dem_o_cell = 1;
                                        index = 1;
                                        while (hang + index < m && arr[hang + index][cot] == o_cell){
                                            dem_o_cell++;
                                            index++;
                                        } index=1;
                                        while(hang - index > 0 && arr[hang - index][cot] == o_cell) {
                                            dem_o_cell++;
                                            index++;
                                        }
                                        switch (dem_o_cell) {
                                            case 5: if (ArrayPointBlock[i][j] < 9)
                                                ArrayPointBlock[i][j] = 9;
                                                break;
                                            case 4: if (ArrayPointBlock[i][j] < 7)
                                                ArrayPointBlock[i][j] = 7;
                                                break;
                                            case 2: if (ArrayPointBlock[i][j] < 1)
                                                ArrayPointBlock[i][j] = 1;
                                                break;
                                        }

                                        // Kiểm tra cột dọc
                                        dem_o_cell=1;
                                        index=1;
                                        while(cot + index < n && arr[hang][cot + index] == o_cell){
                                            dem_o_cell++;
                                            index++;
                                        } index=1;
                                        while(cot - index > 0 && arr[hang][cot - index] == o_cell) {
                                            dem_o_cell++;
                                            index++;
                                        }
                                        switch (dem_o_cell) {
                                            case 5: if (ArrayPointBlock[i][j] < 9)
                                                ArrayPointBlock[i][j] = 9;
                                                break;
                                            case 4: if (ArrayPointBlock[i][j] < 7)
                                                ArrayPointBlock[i][j] = 7;
                                                break;
                                            case 2: if (ArrayPointBlock[i][j] < 1)
                                                ArrayPointBlock[i][j] = 1;
                                                break;
                                        }

                                        dem_o_cell=1;
                                        index=1;
                                        while(hang + index < m && cot + index < n && arr[hang + index][cot + index] == o_cell){
                                            dem_o_cell++;
                                            index++;
                                        } index=1;
                                        while(cot - index > 0 && hang - index > 0 && arr[hang - index][cot - index] == o_cell){
                                            dem_o_cell++;
                                            index++;
                                        }
                                        switch (dem_o_cell) {
                                            case 5: if (ArrayPointBlock[i][j] < 9)
                                                ArrayPointBlock[i][j] = 9;
                                                break;
                                            case 4: if (ArrayPointBlock[i][j] < 7)
                                                ArrayPointBlock[i][j] = 7;
                                                break;
                                            case 2: if (ArrayPointBlock[i][j] < 1)
                                                ArrayPointBlock[i][j] = 1;
                                                break;
                                        }

                                        dem_o_cell=1;
                                        index=1;
                                        while(hang + index < m && cot - index > 0 && arr[hang + index][cot - index] == o_cell){
                                            dem_o_cell++;
                                            index++;
                                        } index=1;
                                        while(cot + index < n && hang - index > 0 && arr[hang - index][cot + index] == o_cell) {
                                            dem_o_cell++;
                                            index++;
                                        }

                                        // Mảng điểm được cho khi thằng máy chặn thằng người
                                        switch (dem_o_cell) {
                                            case 4: if (ArrayPointBlock[i][j] < 9)
                                                ArrayPointBlock[i][j] = 9;
                                                break;
                                            case 3: if (ArrayPointBlock[i][j] < 7)
                                                ArrayPointBlock[i][j] = 7;
                                                break;
                                            case 2: if (ArrayPointBlock[i][j] < 1)
                                                ArrayPointBlock[i][j] = 1;
                                                break;
                                        }
                                    }
                                    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

                                    // Lượt đi của Computer nếu đánh quân "X", người chọn o_cell
                                    if (firstPlayerX == true) {
                                        dem_o_cell = 1;
                                        index = 1;
                                        // Kiểm tra hàng ngang
                                        while (hang + index < m && arr[hang+index][cot]== o_cell){
                                            dem_o_cell++;
                                            index++;
                                        }index=1;
                                        while (hang - index >0 && arr[hang-index][cot]== o_cell){
                                            dem_o_cell++;
                                            index++;
                                        }
                                        switch (dem_o_cell) {
                                            case 5: if (ArrayPointBlock[i][j] < 10)
                                                ArrayPointBlock[i][j] = 10;
                                                break;
                                            case 4: if (ArrayPointBlock[i][j] < 8)
                                                ArrayPointBlock[i][j] = 8;
                                                break;
                                            case 3: if (ArrayPointBlock[i][j] < 6)
                                                ArrayPointBlock[i][j] = 6;
                                                break;
                                            case 2: if (ArrayPointBlock[i][j] < 4)
                                                ArrayPointBlock[i][j] = 4;
                                                break;
                                        }

                                        // Kiểm tra hàng ngang
                                        dem_x_cell = 1;
                                        index = 1;
                                        while (hang + index < m && arr[hang + index][cot] == x_cell){
                                            dem_x_cell++;
                                            index++;
                                        } index=1;
                                        while(hang - index > 0 && arr[hang - index][cot] == x_cell) {
                                            dem_x_cell++;
                                            index++;
                                        }
                                        switch (dem_x_cell) {
                                            case 5: if (ArrayPointBlock[i][j] < 9)
                                                ArrayPointBlock[i][j] = 9;
                                                break;
                                            case 4: if (ArrayPointBlock[i][j] < 7)
                                                ArrayPointBlock[i][j] = 7;
                                                break;
                                            case 2: if (ArrayPointBlock[i][j] < 1)
                                                ArrayPointBlock[i][j] = 1;
                                                break;
                                        }
                                    }
//                                    System.out.println("hang= " + hang + ", cot= " + cot + ", point " + ArrayPointBlock[hang][cot]
//                                            + ", dem_o_cell ="+ dem_o_cell+ ", dem_x_cell ="+ dem_x_cell);

                                    this.invalidate();
                                }

                            }
                        }
                        for (int i = 0; i < ArrayPointBlock.length; i++) {
                            for (int j = 0; j < ArrayPointBlock[0].length; j++) {
                                if (ArrayPointBlock[i][j] > ArrayPointBlock[maxi][maxj]) {
                                    maxi = i;
                                    maxj = j;
                                    ArrayPointBlock[i][j] = ArrayPointBlock[maxi][maxj];
                                }
                            }
                        }
                        int hangcom = maxi;
                        int cotcom = maxj;
                        int dem_o_cell = 1;
                        int dem_x_cell =1;
                        int index =1;
                        if(playerTurn == 0 ){
                            if(firstPlayerX == true) {
                                this.arr[(int) maxi][(int) maxj] = o_cell;
                            }
                            if(firstPlayerX == false){
                                this.arr[(int) maxi][(int) maxj] = x_cell;
                            }
                        }
                        else if(playerTurn == 1){
                            if(firstPlayerX == false){
                                dem_x_cell = 1;
                                index = 1;
                                // Kiểm tra hàng ngang
                                while (hangcom + index < m && arr[hangcom+index][cotcom]== x_cell){
                                    dem_x_cell++;
                                    index++;
                                }index=1;
                                while (hangcom - index >0 && arr[hangcom-index][cotcom]== x_cell){
                                    dem_x_cell++;
                                    index++;
                                }
                                this.arr[(int) maxi][(int) maxj] = x_cell;

                                if (dem_x_cell >=5) {
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Cờ caro");
                                    builder.setIcon(R.mipmap.congra);
                                    //builder.setIcon(R.drawable.images);
                                    builder.setMessage("Máy đã chiến thắng người");
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    builder.create().show();
                                }

                                // Kiểm tra cột dọc
                                dem_x_cell=1;
                                index=1;
                                while (cotcom + index < n && arr[hangcom][cotcom+index]== x_cell){
                                    dem_x_cell++;
                                    index++;
                                }index=1;
                                while (cotcom - index >0 && arr[hangcom][cotcom- index]== x_cell) {
                                    dem_x_cell++;
                                    index++;
                                }
                                if (dem_x_cell >=5) {
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Cờ caro");
                                    builder.setIcon(R.mipmap.congra);
                                    //builder.setIcon(R.drawable.images);
                                    builder.setMessage("Máy đã chiến thắng người");
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    builder.create().show();
                                }


                                // Kiểm tra chéo trái
                                dem_x_cell=1;
                                index=1;
                                while (hangcom + index < m && cotcom + index <n && arr[hangcom+ index][cotcom+index]== x_cell){
                                    dem_x_cell++;
                                    index++;
                                }index=1;
                                while (cotcom - index >0 && hangcom - index > 0 && arr[hangcom- index][cotcom- index]== x_cell) {
                                    dem_x_cell++;
                                    index++;
                                }this.arr[(int) maxi][(int) maxj] = x_cell;
                                if (dem_x_cell >=5) {
                                    System.out.println("dem_x_cell = " + dem_x_cell + ",firstPlayerX == false"+ ",hangcom="+hangcom+",cotcom="+cotcom);
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Cờ caro");
                                    builder.setIcon(R.mipmap.congra);
                                    //builder.setIcon(R.drawable.images);
                                    builder.setMessage("Máy đã chiến thắng người");
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    builder.create().show();
                                }
                                // Kiểm tra chéo phải
                                dem_x_cell=1;
                                index=1;
                                while (hangcom + index < m && cotcom - index > 0 && arr[hangcom+ index][cotcom-index]== x_cell){
                                    dem_x_cell++;
                                    index++;
                                }index=1;
                                while (cotcom + index < n && hangcom - index > 0 && arr[hangcom- index][cotcom+ index]== x_cell){
                                    dem_x_cell++;
                                    index++;
                                }
                                this.arr[(int) maxi][(int) maxj] = x_cell;

                                if (dem_x_cell >=5) {
                                    System.out.println("dem_x_cell = " + dem_x_cell + ",firstPlayerX == false"+ ",hangcom="+hangcom+",cotcom="+cotcom);
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Cờ caro");
                                    builder.setIcon(R.mipmap.congra);
                                    //builder.setIcon(R.drawable.images);
                                    builder.setMessage("Máy đã chiến thắng người");
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    builder.create().show();
                                }
                            }


                            if(firstPlayerX == true){
                                dem_o_cell = 1;
                                index = 1;
                                // Kiểm tra hàng ngang
                                while (hangcom + index < m && arr[hangcom+index][cotcom]== o_cell){
                                    dem_o_cell++;
                                    index++;
                                }index=1;
                                while (hangcom - index >0 && arr[hangcom-index][cotcom]== o_cell){
                                    dem_o_cell++;
                                    index++;
                                }
                                this.arr[(int) maxi][(int) maxj] = o_cell;
                                if (dem_o_cell >=5) {
                                    System.out.println("dem_o_cell = " + dem_o_cell + "firstPlayerX == false");
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Cờ caro");
                                    builder.setIcon(R.mipmap.congra);
                                    //builder.setIcon(R.drawable.images);
                                    builder.setMessage("Máy đã chiến thắng người");
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    builder.create().show();
                                }
                                // Kiểm tra cột dọc
                                dem_o_cell=1;
                                index=1;
                                while (cotcom + index < n && arr[hangcom][cotcom+index]== o_cell){
                                    dem_o_cell++;
                                    index++;
                                }index=1;
                                while (cotcom - index >0 && arr[hangcom][cotcom- index]== o_cell) {
                                    dem_o_cell++;
                                    index++;
                                }this.arr[(int) maxi][(int) maxj] = o_cell;
                                if (dem_o_cell >=5) {
                                    System.out.println("dem_o_cell = " + dem_o_cell + "firstPlayerX == false");
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Cờ caro");
                                    builder.setIcon(R.mipmap.congra);
                                    //builder.setIcon(R.drawable.images);
                                    builder.setMessage("Máy đã chiến thắng người");
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    builder.create().show();
                                }
                                // Kiểm tra chéo trái
                                dem_o_cell=1;
                                index=1;
                                while (hangcom + index < m && cotcom + index <n && arr[hangcom+ index][cotcom+index]== o_cell){
                                    dem_o_cell++;
                                    index++;
                                }index=1;
                                while (cotcom - index >0 && hangcom - index > 0 && arr[hangcom- index][cotcom- index]== o_cell) {
                                    dem_o_cell++;
                                    index++;
                                }this.arr[(int) maxi][(int) maxj] = o_cell;
                                if (dem_o_cell >=5) {
                                    System.out.println("dem_o_cell = " + dem_o_cell + "firstPlayerX == false");
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Cờ caro");
                                    builder.setIcon(R.mipmap.congra);
                                    //builder.setIcon(R.drawable.images);
                                    builder.setMessage("Máy đã chiến thắng người");
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    builder.create().show();
                                }
                                // Kiểm tra chéo phải
                                dem_o_cell=1;
                                index=1;
                                while (hangcom + index < m && cotcom - index > 0 && arr[hangcom+ index][cotcom-index]== o_cell){
                                    dem_o_cell++;
                                    index++;
                                }index=1;
                                while (cotcom + index < n && hangcom - index > 0 && arr[hangcom- index][cotcom+ index]== o_cell){
                                    dem_o_cell++;
                                    index++;
                                }this.arr[(int) maxi][(int) maxj] = o_cell;

                                if (dem_o_cell >=5) {
                                    System.out.println("dem_o_cell = " + dem_o_cell + "firstPlayerX == true");
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Cờ caro");
                                    builder.setIcon(R.mipmap.congra);
                                    //builder.setIcon(R.drawable.images);
                                    builder.setMessage("Máy đã chiến thắng người");
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    builder.create().show();
                                }
                            }
                        }
                        this.invalidate();

                        // Chuyển nước đi cho Human đánh
                        playerTurn = 0;
                        System.out.println(" Chuyển cho thằng Human đánh");
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Cờ caro");
                        builder.setIcon(R.mipmap.warning);
                        builder.setMessage("Đánh sai xin mời đánh lại");
                        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.create().show();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("Action move");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("Action up");
                break;
            case MotionEvent.ACTION_CANCEL:
                System.out.println("Action cancel");
                break;
            case MotionEvent.ACTION_OUTSIDE:
                System.out.println("Action outside");
                break;
        }
        return super.onTouchEvent(event);
    }
    //Source code by LamNguyen



}
