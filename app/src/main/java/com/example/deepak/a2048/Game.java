package com.example.deepak.a2048;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.*;

/**
 * Created by Deepak on 04-Jan-18.
 */

public class Game extends AppCompatActivity{

    HashMap<String, Integer> map= null;
    HashMap<String, Integer> prevmap= null;
    GridView grid=null;
    public Game(HashMap m)
    {
        map=m;
    }
    public Game()
    {
        map=new HashMap<>();
    }
    int score=0;
    TextView t=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        t=(TextView) findViewById(R.id.Score);
        grid=(GridView)findViewById(R.id.gamebackground);
        Random random1=new Random();
        int r1 = random1.nextInt(16-1+1)+1;
        Random random2=new Random();
        int r2 = random2.nextInt(16-1+1)+1;
        if(r1==r2 && r2!=16)
        {
            r2++;
        }
        else
        {
            if(r1==r2)
            {
                r2--;
            }
        }
        Log.i("TAG",r1+"    "+r2);
        // Initializing a new String Array

        String[] nums = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16"};
        Random r3 = new Random();
        Random r4 = new Random();
        int[] choice={2,4};
        int ind1=r3.nextInt(choice.length);
        int ind2=r3.nextInt(choice.length);

//        Log.i("TAG",choice[ind1]+"     "+choice[ind2]);
        for(int i=0 ;i<nums.length;i++)
        {
            if(Integer.parseInt(nums[i])==r1)
            {
                map.put(nums[i],choice[ind1]);
            }
            if(Integer.parseInt(nums[i])==r2)
            {
                map.put(nums[i],choice[ind2]);
            }
            if(Integer.parseInt(nums[i])!=r2 && Integer.parseInt(nums[i])!=r1)
            {
                map.put(nums[i],0);
            }

        }
//        Log.i("TAG",map.toString());
        // Populate a List from Array elements
        final List<String> plantsList = new ArrayList<String>(Arrays.asList(nums));

        // Data bind GridView with ArrayAdapter (String Array elements)
        gridChanged(plantsList);


       grid.setOnTouchListener(new OnSwipeTouchListener(Game.this){
           public void onSwipeTop() {
               prevmap=new HashMap<>(map);
               Log.i("TAG","TOP");
               Log.i("TAG",map.toString());
               Log.i("TAG",allOkTop(1) +" "+ allOkTop(2) +" "+ allOkTop(3) +" "+ allOkTop(4));
               int k=0;
               while(!(allOkTop(1) && allOkTop(2) && allOkTop(3) && allOkTop(4))) {
                   k=1;
                   for (int i = 16; i > 4; i--) {
                       int j = i;
                       if (j > 4 && map.get(j + "") == map.get((j - 4) + "")) {
//                           Log.i("TAG", map.get(j + "") + map.get((j - 4) + "") + "");
                           map.put((j - 4) + "", map.get(j + "") + map.get((j - 4) + ""));
                           map.put(j + "", 0);
                           String score=t.getText().toString();
                           int scorenumber=Integer.parseInt(score);
                           scorenumber+=map.get((j-4)+"");
                           t.setText(scorenumber+"");
                       } else if (j > 4 && map.get((j - 4) + "") == 0) {
//                           Log.i("TAG", "YES");
                           map.put((j - 4) + "", map.get(j + ""));
                           map.put(j + "", 0);
                       }
                   }
               }
               Log.i("TAG",map.toString());

                gridChanged(plantsList);
                generateRandomNumber();
                checkSwipe();
           }
           public void onSwipeRight() {
               prevmap=new HashMap<>(map);
               Log.i("TAG","RIGHT");
               Log.i("TAG",map.toString());
               Log.i("TAG",allOkRight(4) +" "+ allOkRight(8) +" "+ allOkRight(12) +" "+ allOkRight(16));
               int k=0;
               int l=0;
               while(l==0 || !(allOkRight(4) && allOkRight(8) && allOkRight(12) && allOkRight(16))) {
                   l=1;
                   for (int i = 1; i < 17; i++) {
                       int j = i;
//                       Log.i("TAG", j + "");

                       if (k == 0 && map.get(j + "") == map.get((j + 1) + "")) {
                           map.put((j + 1) + "", map.get(j + "") + map.get((j + 1) + ""));
                           map.put(j + "", 0);
                           String score=t.getText().toString();
                           int scorenumber=Integer.parseInt(score);
                           Log.i("TAG",scorenumber+"HERE");
                           scorenumber+=map.get((j+1)+"");
                           Log.i("TAG",scorenumber+"HERE");
                           t.setText(scorenumber+"");
                       } else if (k == 0 && map.get((j + 1) + "") == 0) {
//                           Log.i("TAG", "YES");
                           map.put((j + 1) + "", map.get(j + ""));
                           map.put(j + "", 0);
                       }
                       if ((j / 4) != (j + 1) / 4) {
                           k = 1;
                       } else {
                           k = 0;
                       }
                   }
               }
               Log.i("TAG",map.toString());
               gridChanged(plantsList);
               generateRandomNumber();
               checkSwipe();
           }
           public void onSwipeLeft() {
               prevmap=new HashMap<>(map);
               Log.i("TAG","LEFT");
               Log.i("TAG",map.toString());
               Log.i("TAG",allOkLeft(1) +" "+ allOkLeft(5) +" "+ allOkLeft(9) +" "+ allOkLeft(13));
               int k=0;
               int l=0;
               while(l==0 || !(allOkLeft(1) && allOkLeft(5) && allOkLeft(9) && allOkLeft(13))) {
                   l=1;
                   for (int i = 16; i > 1; i--) {
                       int j = i;
                       if (j == 13 || j == 9 || j == 5) {
                           k = 1;
                       } else {
                           k = 0;
                       }
//                       Log.i("TAG", j + "");

                       if (k == 0 && map.get(j + "") == map.get((j - 1) + "")) {
                           map.put((j - 1) + "", map.get(j + "") + map.get((j - 1) + ""));
                           map.put(j + "", 0);
                           String score=t.getText().toString();
                           int scorenumber=Integer.parseInt(score);
                           scorenumber+=map.get((j-1)+"");
                           t.setText(scorenumber+"");
                       } else if (k == 0 && map.get((j - 1) + "") == 0) {
//                           Log.i("TAG", "YES");
                           map.put((j - 1) + "", map.get(j + ""));
                           map.put(j + "", 0);
                       }

                   }
               }
               Log.i("TAG",map.toString());
               gridChanged(plantsList);
               generateRandomNumber();
               checkSwipe();
           }
           public void onSwipeBottom() {
               prevmap=new HashMap<>(map);
               Log.i("TAG", "BOTTOM");
               Log.i("TAG",map.toString());
               Log.i("TAG",allOkBottom(13) +" "+ allOkBottom(14) +" "+ allOkBottom(15) +" "+ allOkBottom(16));
               int k=0;
               while (k==0 || !(allOkBottom(13) && allOkBottom(14) && allOkBottom(15) && allOkBottom(16))) {
                   k=1;
                   for (int i = 1; i < 13; i++) {
                       int j = i;

                       if (j < 13 && map.get(j + "") == map.get((j + 4) + "")) {
//                           Log.i("TAG", map.get(j + "") + map.get((j + 4) + "") + "");
                           map.put((j + 4) + "", map.get(j + "") + map.get((j + 4) + ""));
                           map.put(j + "", 0);
                           String score=t.getText().toString();
                           int scorenumber=Integer.parseInt(score);
                           scorenumber+=map.get((j+4)+"");
                           t.setText(scorenumber+"");
                       } else if (j < 13 && map.get((j + 4) + "") == 0) {
//                           Log.i("TAG", "YES");
                           map.put((j + 4) + "", map.get(j + ""));
                           map.put(j + "", 0);
                       }
                   }
               }
               Log.i("TAG", map.toString());

               gridChanged(plantsList);
               generateRandomNumber();
               checkSwipe();
           }
       });

    }
    public boolean allOkTop(int loc)
    {
     if(map.get(loc+"")==0 && map.get((loc+4)+"")==0 && map.get(""+(loc+8))==0 && map.get(""+(loc+12))==0)
     {
         return true;
     }
     else if(map.get((loc+4)+"")==0 && map.get(""+(loc+8))==0 && map.get(""+(loc+12))==0 && map.get(""+loc)!=0)
     {
         return true;
     }
     else if(map.get(""+(loc+8))==0 && map.get(""+(loc+12))==0 && map.get(""+(loc+4))!=0 && map.get(""+loc)!=0)
     {
         return true;
     }
     else if(map.get(""+(loc+12))==0 && map.get(""+(loc+4))!=0 && map.get(""+loc)!=0 && map.get(""+(loc+8))!=0)
     {
         return true;
     }
     else if(map.get(loc+"")!=0 && map.get((loc+4)+"")!=0 && map.get(""+(loc+8))!=0 && map.get(""+(loc+12))!=0)
     {
         return true;
     }
     return false;
    }
    public boolean allOkBottom(int loc)
    {
        if(map.get(loc+"")==0 && map.get((loc-4)+"")==0 && map.get(""+(loc-8))==0 && map.get(""+(loc-12))==0)
        {
            return true;
        }
        else if(map.get((loc-4)+"")==0 && map.get(""+(loc-8))==0 && map.get(""+(loc-12))==0 && map.get(""+loc)!=0)
        {
            return true;
        }
        else if(map.get(""+(loc-8))==0 && map.get(""+(loc-12))==0 && map.get(""+(loc-4))!=0 && map.get(""+loc)!=0)
        {
            return true;
        }
        else if(map.get(""+(loc-12))==0 && map.get(""+(loc-4))!=0 && map.get(""+loc)!=0 && map.get(""+(loc-8))!=0)
        {
            return true;
        }
        else if(map.get(loc+"")!=0 && map.get((loc-4)+"")!=0 && map.get(""+(loc-8))!=0 && map.get(""+(loc-12))!=0)
        {
            return true;
        }
        return false;
    }
    public boolean allOkRight(int loc)
    {
        if(map.get(loc+"")==0 && map.get((loc-1)+"")==0 && map.get(""+(loc-2))==0 && map.get(""+(loc-3))==0)
        {
            return true;
        }
        else if(map.get((loc-1)+"")==0 && map.get(""+(loc-2))==0 && map.get(""+(loc-3))==0 && map.get(""+loc)!=0)
        {
            return true;
        }
        else if(map.get(""+(loc-2))==0 && map.get(""+(loc-3))==0 && map.get(""+(loc-1))!=0 && map.get(""+loc)!=0)
        {
            return true;
        }
        else if(map.get(""+(loc-3))==0 && map.get(""+(loc-1))!=0 && map.get(""+loc)!=0 && map.get(""+(loc-2))!=0)
        {
            return true;
        }
        else if(map.get(loc+"")!=0 && map.get((loc-1)+"")!=0 && map.get(""+(loc-2))!=0 && map.get(""+(loc-3))!=0)
        {
            return true;
        }
        return false;
    }
    public boolean allOkLeft(int loc)
    {
        if(map.get(loc+"")==0 && map.get((loc+1)+"")==0 && map.get(""+(loc+2))==0 && map.get(""+(loc+3))==0)
        {
            return true;
        }
        else if(map.get((loc+1)+"")==0 && map.get(""+(loc+2))==0 && map.get(""+(loc+3))==0 && map.get(""+loc)!=0)
        {
            return true;
        }
        else if(map.get(""+(loc+2))==0 && map.get(""+(loc+3))==0 && map.get(""+(loc+1))!=0 && map.get(""+(loc))!=0)
        {
            return true;
        }
        else if(map.get(""+(loc+3))==0 && map.get(""+(loc+1))!=0 && map.get(""+(loc))!=0 && map.get(""+(loc+2))!=0)
        {
            return true;
        }
        else if(map.get(loc+"")!=0 && map.get((loc+1)+"")!=0 && map.get(""+(loc+2))!=0 && map.get(""+(loc+3))!=0)
        {
            return true;
        }
        return false;
    }
    public void checkSwipe()
    {
        ConstraintLayout cl=(ConstraintLayout) findViewById(R.id.wholelayout);
        LinearLayout lll= new LinearLayout(this);
        lll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        lll.setBackgroundColor(getResources().getColor(R.color.translucent));
        lll.setGravity(Gravity.CENTER);
        TextView result= new TextView(this);
        for(String key : map.keySet())
        {
            if(map.get(key)==2048)
            {
                result.setText("CONGRATULATIONS! YOU WIN!");
                result.setGravity(Gravity.CENTER);
                lll.addView(result);
                cl.addView(lll);
                Intent i= new Intent(Game.this,ResultPage.class);
                i.putExtra("Result",0);
                startActivity(i);
                return;
            }
        }
        Log.e("MAP",map.toString());
        Log.e("PREVMAP",prevmap.toString());
        for(String key : map.keySet())
        {
            if(map.get(key)!=prevmap.get(key))
            {

                return;
            }
        }

        result.setText("GAME OVER! YOU LOSE!");
        result.setGravity(Gravity.CENTER);
        lll.addView(result);
        cl.addView(lll);
//        prevmap=map;
        Intent i= new Intent(Game.this,ResultPage.class);
        i.putExtra("Result",1);
        startActivity(i);

    }
    public void generateRandomNumber()
    {
        Random ran = new Random();
        int i=ran.nextInt(16)+1;
        Log.i("TAG1",map.toString());
        int flag=0;
        for(String key: map.keySet())
        {
            if(map.get(key)!=0)
            {
                flag++;
            }
        }
        if(flag!=16)
        {
            while(map.get(i+"")!=0)
            {
                i=ran.nextInt(16)+1;
            }
            int[] choice={2,4};
            int ind1=ran.nextInt(choice.length);
            Log.i("TAG1",choice[ind1]+"    "+i);
            map.put(i+"",choice[ind1]);
            String[] nums = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16"};
            Log.i("TAG1",map.toString());        List<String> plantsList=Arrays.asList(nums);

            gridChanged(plantsList);
        }

    }
    public void gridChanged(List plantsList)
    {
        final List<String> l=plantsList;
        grid.setAdapter(new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, l){
            public View getView(int position, View convertView, ViewGroup parent) {

                // Return the GridView current item as a View
                View view = super.getView(position,convertView,parent);

                // Convert the view as a TextView widget
                TextView tv = (TextView) view;

                //tv.setTextColor(Color.DKGRAY);

                // Set the layout parameters for TextView widget
                RelativeLayout.LayoutParams lp =  new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT
                );
                tv.setLayoutParams(lp);

                tv.setWidth(100);
                tv.setHeight(210);

                // Display TextView text in center position
                tv.setGravity(Gravity.CENTER);

                tv.setTextSize(20);

                // Set the TextView text (GridView item text)
                if(map.get(l.get(position))!=0)
                {
                    Log.i("TAG",position+"");
                    Log.i("TAG",l.get(position));
                    tv.setText(map.get(l.get(position))+"");
                }
                else
                {
                    tv.setText("");
                }

                tv.setId(position);

                // Set the TextView background color
                tv.setBackgroundColor(Color.parseColor("#b2dfdb"));

                // Return the TextView widget as GridView item
                return tv;
            }
        });
    }
}
