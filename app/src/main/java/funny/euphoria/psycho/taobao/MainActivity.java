package funny.euphoria.psycho.taobao;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.crypto.NoSuchPaddingException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class MainActivity extends Activity {


    private ClipboardManager mClipboardManager;

    private EditText mSearchText;
    private EditText mContentText;
    private Button mSearchButton;
    private Button mMakeButton;
    private Button mUpdateButton;

    private DatabaseHelper mDatabaseHelper;

    private boolean shouldAskPermission() {

        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (shouldAskPermission()) {
            String[] perms = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};

            int permsRequestCode = 200;
            requestPermissions(perms, permsRequestCode);
        }
        String dataFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/datas.db";

        mDatabaseHelper = new DatabaseHelper(this, dataFileName);

        mClipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        mSearchButton = (Button) findViewById(R.id.search_btn);
        mMakeButton = (Button) findViewById(R.id.make_btn);
        mContentText = (EditText) findViewById(R.id.editText_content);
        mSearchText = (EditText) findViewById(R.id.editText_query);
        mUpdateButton = (Button) findViewById(R.id.update_btn);
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mContentText.getText().toString();

                if (content.length() > 0) {
                    String[] array = content.split("\n");
                    mDatabaseHelper.updateItem(array);
                } else {
                    String search = mSearchText.getText().toString().trim();

                    try {
                        CryptLib cryptLib=new CryptLib();
                    String encrypted=    cryptLib.encrypt(search,"a3106b1138cf947f69b7038942976a3", "EEGTuhyHRaFrQMhl");
                        mContentText.setText(encrypted);
                        mClipboardManager.setPrimaryClip(ClipData.newPlainText("",encrypted));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = mSearchText.getText().toString().trim();
                if (search.length() > 0) {
                    mContentText.setText(mDatabaseHelper.queryItem(search));
                }
            }
        });
        mMakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mContentText.getText().toString().trim();
                if (content.length() > 0) {
                    String[] array = content.split("\n", 5);
                    StringBuilder stringBuilder = new StringBuilder(512);
                    stringBuilder.append("【Lynda 高清视频教程】").append(array[0]).append("\n");
                    if (array[1].length() > 0) {
                        stringBuilder.append("下载链接：").append(array[1].replace("链接：", "")).append("\n");
                    }
                    if (array[2].length() > 0) {
                        stringBuilder.append("解压密码：").append(array[2]).append("\n");
                    }
                    if (array[3].length() > 0) {
                        stringBuilder.append("练习文件：").append(array[3].replace("链接：", "")).append("\n");
                    }
                    mClipboardManager.setPrimaryClip(ClipData.newPlainText("", stringBuilder.toString()));
                }
            }
        });
    }

    private void initialDid() {

//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String str = mList.get(position);
//                String[] ls = str.split("\\|", 5);
//                if (ls.length >= 5) {
//                    StringBuilder stringBuilder = new StringBuilder(512);
//                    stringBuilder.append("【Lynda 高清视频教程】").append(ls[0]).append("\n")
//                            .append("下载链接：").append(ls[1]).append("\n")
//                            .append("解压密码：").append(ls[2]).append("\n")
//                            .append("练习文件：").append(ls[3]).append("\n");
//                    mClipboardManager.setPrimaryClip(ClipData.newPlainText("", stringBuilder.toString()));
//
//                }
//
//            }
//        });
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {

        switch (permsRequestCode) {

            case 200:
                break;
        }

    }


    public ArrayList<String> lines(String fullName) {
        ArrayList<String> ls = new ArrayList<>();

        try {
            File file = new File(fullName);
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().length() > 0)
                        ls.add(line);
                }
                br.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return ls;
    }
}
