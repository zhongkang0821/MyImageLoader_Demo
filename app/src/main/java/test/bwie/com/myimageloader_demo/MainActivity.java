package test.bwie.com.myimageloader_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    private GridView myrecycler;
    private PhotoWallAdapter mAdapter;

    private int mImageThumbSize;
    private int mImageThumbSpacing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageThumbSize = getResources().getDimensionPixelSize(
                R.dimen.image_thumbnail_size);
        mImageThumbSpacing = getResources().getDimensionPixelSize(
            R.dimen.image_thumbnail_spacing);
        myrecycler = (GridView) findViewById(R.id.myrecycler);
//        myrecycler.setLayoutManager(new GridLayoutManager(this,5));
        mAdapter = new PhotoWallAdapter(this,0,Images.imageThumbUrls,myrecycler);
        myrecycler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent m=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(m);
            }
        });
        myrecycler.setAdapter(mAdapter);
        myrecycler.getViewTreeObserver().addOnGlobalLayoutListener(
            new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout(){
                final int numColumns = (int) Math.floor(myrecycler.getWidth()
                    / (mImageThumbSize + mImageThumbSpacing));
                if (numColumns > 0) {
                    int columnWidth = (myrecycler.getWidth() / numColumns)
                     - mImageThumbSpacing;
                     mAdapter.setItemHeight(columnWidth);
                    myrecycler.getViewTreeObserver()
                    .removeGlobalOnLayoutListener(this);
                    }
                }
            });
        }

    @Override
    protected void onPause() {
        super.onPause();
        mAdapter.fluchCache();
        }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出程序时结束所有的下载任务  
        mAdapter.cancelAllTasks();
    }
}
