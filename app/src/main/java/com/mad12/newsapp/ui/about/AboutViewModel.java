package com.mad12.newsapp.ui.about;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AboutViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AboutViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Sinh viên thực hiện:\n" +
                "Đinh Văn Đô: B18DCCN157\n" +
                "Trần Xuân Hiệp: B18DCCN212\n" +
                "Nguyễn Trọng Hiếu: B18DCCN219\n" +
                "Phạm Công Thành: B18DCCN619\n\n" +
                "Trong những năm gần đây, với sự bùng nổ của Internet, chúng ta có thể dễ dàng thực hiện nhiều việc mà vốn trước đây cần tốn rất nhiều thời gian và công sức trong đó vó việc đọc tin tức. Việc đọc tin tức hàng ngày giúp chúng ta cập nhật những thông tin mới nhất về thời sự, chính trị, thể thao, thế giới, xã hội, ... một cách nhanh nhất và chính xác. Vì vậy, nhóm quyết định xây dựng app tin tức với tên gọi là News App giúp cho người dùng đọc tin tức hàng ngày một cách thuận tiện nhất.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}