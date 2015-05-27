package com.example.geoquiz;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
public class QuizActivity extends Activity {

	
	private Button mTrueButton;
	private Button mFalseButton;
	private ImageButton mNextButton;
	private ImageButton mPrevButton;
	private TextView mQuestionTextView;
	
	private Button mCheatButton;
	
	private boolean mIsCheater;
	
	private TrueFalse[] mQuestionBank = new TrueFalse[] {
			new TrueFalse(R.string.question_oceans, true),
			new TrueFalse(R.string.question_mideast, false),
			new TrueFalse(R.string.question_africa, false),
			new TrueFalse(R.string.question_asia, true),
			new TrueFalse(R.string.question_americas, true)
	};
	
	private int mCurrentIndex = 0;
	
	private void updateQuestion(){
		int question = mQuestionBank[mCurrentIndex].getQuestion();
		mQuestionTextView.setText(question);
	}
	
	private void checkAnswer(boolean userPressedTrue){
		
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
		
		int messageResId = 0;
		
		if(mIsCheater){
			messageResId = R.string.judgment_toast;
		}
		else
		{
			if(userPressedTrue == answerIsTrue){
				messageResId = R.string.correct_toast;
			}
			else{
				messageResId = R.string.incorrect_toast;
			}
		}
		Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
	}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        mTrueButton = (Button)findViewById(R.id.true_button);
        mFalseButton = (Button)findViewById(R.id.false_button);
        
        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
	    
        mTrueButton.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View v){
        		checkAnswer(true);
        	}
        	
        });
        
        mFalseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				checkAnswer(false);
			}
		});
        
        mNextButton = (ImageButton)findViewById(R.id.next_imagebutton);
        mNextButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
				mIsCheater = false;
				updateQuestion();
				
			}
		});
        
        
	    mPrevButton = (ImageButton)findViewById(R.id.prev_imagebutton);	
	    mPrevButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mCurrentIndex > 0){
					mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
					updateQuestion();
				}
			}
		});
        
	    
	    mCheatButton = (Button)findViewById(R.id.cheat_button);
	    mCheatButton.setOnClickListener(new View.OnClickListener(){
	    	
	    	@Override
	    	public void onClick(View v){
	    		Intent i = new Intent(QuizActivity.this, CheatActivity.class);
	    		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
	    		i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
	    		//startActivity(i);
	    		startActivityForResult(i, 0);
	    	}
	    	
	    });
	    
        updateQuestion();
    }

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	if(data == null){
    		return;
    	}
    	mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
    }
}
