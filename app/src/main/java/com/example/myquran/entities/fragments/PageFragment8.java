package com.example.myquran.entities.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myquran.MyQuranApp;
import com.example.myquran.R;
import com.example.myquran.connectionbd.DataBaseHelper;
import com.example.myquran.entities.model.Functions;
import com.example.myquran.entities.model.StyleCallback;

import java.util.ArrayList;
import java.util.List;

public class PageFragment8 extends Fragment {
    String json;
    final int numero=569;
    int cptShow=2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        json = bundle.getString("json");
        String pagetitre = bundle.getString("titre");

        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.page_8, container, false);
        TextView pageTitle=rootView.findViewById(R.id.surahName);
        final TextView surhText = rootView.findViewById(R.id.surahText);
        TextView pageNum=rootView.findViewById(R.id.pageNum);
        pageNum.setText("569");
        SpannableString s1= new SpannableString(json);
        for (int i : Functions.GetPosAyah(json)){
            s1.setSpan(new StyleSpan(Typeface.BOLD),i-1,i+2,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        surhText.setText(s1);
        pageTitle.setText(pagetitre);

        TextView hideBtn=rootView.findViewById(R.id.hideBtn);//button li nkhebbi bih
        TextView showBtn=rootView.findViewById(R.id.showBtn);//button li n affichi bih
        surhText.setCustomSelectionActionModeCallback(new StyleCallback(surhText,numero));

        final List<String> subStringList=new ArrayList<>();
        final ForegroundColorSpan fcsWhite=new ForegroundColorSpan(Color.WHITE);
        final ForegroundColorSpan fcsBlack=new ForegroundColorSpan(Color.BLACK);

        for (int i=11;i<40;i++){
            subStringList.add(Functions.ChangetoArabic(i));

        }







        final String fullText=surhText.getText().toString();//all the page text

        hideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//when we click on hide brn
                SpannableString fullSpanneble=new SpannableString(fullText);//SpannableString pour pouvez changez la couleur
                /*String subString="سورة الملك";*/
                /*Toast.makeText(rootView.getContext(),"cliicked",Toast.LENGTH_SHORT).show();*/

                fullSpanneble.setSpan(fcsWhite,0,fullSpanneble.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//hada bach nkhebi kolch w naffichi ghir wach lazem
                for (int i :Functions.GetPosAyah(json)){
                    fullSpanneble.setSpan(new StyleSpan(Typeface.BOLD),i-1,i+2,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                for (int i = 0; i <subStringList.size() ; i++) {
                    ForegroundColorSpan fcsblack=new ForegroundColorSpan(Color.BLACK);
                    int startIndex =fullText.indexOf(subStringList.get(i));
                    fullSpanneble.setSpan(fcsblack,startIndex,startIndex+subStringList.get(i).length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }



                DataBaseHelper dataBaseHelper=new DataBaseHelper(rootView.getContext());
                dataBaseHelper.deleteSubStringsPagesStat(numero);


                cptShow=0;
                surhText.setText(fullSpanneble);

                ((MyQuranApp) getActivity().getApplication()).setSurahText(surhText);

            }
        });


        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//show btn
                String fullText=surhText.getText().toString();
                SpannableString fullSpanneble=new SpannableString(fullText);
                //   cptShow=2;
                fullSpanneble.setSpan(fcsWhite,0,fullSpanneble.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                for (int i :Functions.GetPosAyah(json)){
                    fullSpanneble.setSpan(new StyleSpan(Typeface.BOLD),i-1,i+2,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    fullSpanneble.setSpan(new ForegroundColorSpan(Color.BLACK),i-1,i+2,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                switch (cptShow){

                    case 0://  showing the first word
                        cptShow++;
                        for (int i = 0; i <subStringList.size()-1 ; i++) {
                            ForegroundColorSpan fcsblack=new ForegroundColorSpan(Color.BLACK);
                            int startIndex =fullText.indexOf(subStringList.get(i));
                            if (i==0) // first ayah in page
                                fullSpanneble.setSpan(fcsBlack,0,Functions.GetIndexOfFirstWord(json,0),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            fullSpanneble.setSpan(fcsblack,startIndex,startIndex+subStringList.get(i).length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            fullSpanneble.setSpan(new ForegroundColorSpan(Color.BLACK),startIndex+4,Functions.GetIndexOfFirstWord(json,startIndex+5),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        }
                        ForegroundColorSpan fcsblack1=new ForegroundColorSpan(Color.BLACK);
                        int startIndex1 =fullText.indexOf(subStringList.get(subStringList.size()-1));//nafiichi numero laakher
                        fullSpanneble.setSpan(fcsblack1,startIndex1,startIndex1+subStringList.get(subStringList.size()-1).length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        break;
                    case 1:// showing  2 first words and last word
                        cptShow++;
                        for (int i = 0; i <subStringList.size()-1 ; i++) {
                            ForegroundColorSpan fcsblack=new ForegroundColorSpan(Color.BLACK);
                            int startIndex =fullText.indexOf(subStringList.get(i));
                            fullSpanneble.setSpan(fcsblack,startIndex,startIndex+subStringList.get(i).length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            fullSpanneble.setSpan(new ForegroundColorSpan(Color.BLACK),startIndex+4,Functions.GetIndexOfSecondWord(json,startIndex+5),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            fullSpanneble.setSpan(new ForegroundColorSpan(Color.BLACK),Functions.GetIndexOfLastWord(json,startIndex),startIndex,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        }

                        ForegroundColorSpan fcsblack=new ForegroundColorSpan(Color.BLACK);
                        ForegroundColorSpan fcsblack2=new ForegroundColorSpan(Color.BLACK);
                        fullSpanneble.setSpan(fcsblack2,0,Functions.GetIndexOfSecondWord(json,18),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        int startIndex =fullText.indexOf(subStringList.get(subStringList.size()-1));
                        fullSpanneble.setSpan(fcsblack,Functions.GetIndexOfLastWord(json,startIndex),startIndex,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        break;
                    case 2:
                        fullSpanneble.setSpan(fcsBlack,0,fullSpanneble.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        Toast.makeText(rootView.getContext(),String.valueOf(cptShow),Toast.LENGTH_SHORT).show();

                        //     cptShow=0;

                }



                surhText.setText(fullSpanneble);


            }
        });






        return rootView;
    }

}
