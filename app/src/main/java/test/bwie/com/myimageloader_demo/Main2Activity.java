package test.bwie.com.myimageloader_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewTreeObserver;

public class Main2Activity extends AppCompatActivity {

    private RecyclerView viewrecycler;
    private recyclerAdapter adapter;

    private int mImageThumbSize;
    private int mImageThumbSpacing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mImageThumbSize = getResources().getDimensionPixelSize(
                R.dimen.image_thumbnail_size);
        mImageThumbSpacing = getResources().getDimensionPixelSize(
                R.dimen.image_thumbnail_spacing);
        viewrecycler = (RecyclerView) findViewById(R.id.viewrecycler);
        viewrecycler.setLayoutManager(new GridLayoutManager(this,3));
        adapter = new recyclerAdapter(this,Images.imageThumbUrls,
                viewrecycler);
        viewrecycler.setAdapter(adapter);
        viewrecycler.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        final int numColumns = (int) Math.floor(viewrecycler
                                .getWidth()
                                / (mImageThumbSize + mImageThumbSpacing));
                        if (numColumns > 0) {
                            int columnWidth = (viewrecycler.getWidth() / numColumns)
                                    - mImageThumbSpacing;
                            adapter.setItemHeight(columnWidth);
                            viewrecycler.getViewTreeObserver()
                                    .removeGlobalOnLayoutListener(this);
                        }
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        adapter.fluchCache();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出程序时结束所有的下载任务
        adapter.cancelAllTasks();
    }
}
