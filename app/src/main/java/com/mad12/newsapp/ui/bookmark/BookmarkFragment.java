package com.mad12.newsapp.ui.bookmark;

import static android.content.Context.MODE_APPEND;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;

import com.mad12.newsapp.R;
import com.mad12.newsapp.databinding.FragmentBookmarkBinding;
import com.mad12.newsapp.model.Article;
import com.mad12.newsapp.model.Category;
import com.mad12.newsapp.ui.articleContent.ArticleContentActivity;
import com.mad12.newsapp.ui.home.ArticleViewPagerAdapter;
import com.mad12.newsapp.ui.search.SearchActivity;
import com.mad12.newsapp.ui.search.SearchListAdapter;
import com.mad12.newsapp.utils.RetrofitClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookmarkFragment extends Fragment {

    private FragmentBookmarkBinding binding;
    List<String> arraysId = new ArrayList<>();
    ListView listViewSaved;
    TextView deleteBookmark;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBookmarkBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listViewSaved = root.findViewById(R.id.listArticleSaved);
        deleteBookmark = root.findViewById(R.id.deleteBookmark);

        //delete all articles saved
        File dir = getContext().getFilesDir();
        File file = new File(dir, "archive.txt");
        if(file.exists()){
            deleteBookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            getContext());
                    alert.setMessage("Bạn có muốn xóa hết");
                    alert.setPositiveButton("Xóa hết", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            file.delete();
                            arraysId = new ArrayList<>();
                            getArticlesSaved(arraysId);
                        }
                    });
                    alert.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alert.show();
                }
            });
        }

        //get list id from internal storage
        try {
            FileOutputStream fOut = getContext().openFileOutput("archive.txt", MODE_APPEND);
            PrintWriter writer = new PrintWriter( new OutputStreamWriter( fOut ) );
            FileInputStream fis = getContext().openFileInput("archive.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while((line = reader.readLine()) != null){
                arraysId.add(line);
            }
            reader.close();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //show articles saved
        getArticlesSaved(arraysId);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //Call API
    private void getArticlesSaved(List<String> arrays) {
        Call<List<Article>> call = RetrofitClient.getInstance().getMyApi().getArticlesSaved(arrays);
        call.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                BookmarkListAdapter adapter = new BookmarkListAdapter(getActivity(),  response.body());
                listViewSaved.setAdapter(adapter);
                listViewSaved.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getActivity(), ArticleContentActivity.class);
                        intent.putExtra("article_id", response.body().get(i).getId());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Log.println(Log.DEBUG,"error call api", t.toString());
            }
        });
    }

}