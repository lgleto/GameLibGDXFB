package ipca.game.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import mk.gdx.firebase.GdxFIRAuth;
import mk.gdx.firebase.GdxFIRDatabase;
import mk.gdx.firebase.auth.GdxFirebaseUser;
import mk.gdx.firebase.callbacks.AuthCallback;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		GdxFIRAuth.instance().signInAnonymously(new AuthCallback() {
			@Override
			public void onSuccess(GdxFirebaseUser user)
			{
				// Deal with with current user.
				String userDisplayName = user.getUserInfo().getDisplayName();
				System.out.println("GdxFIRAuth:"+userDisplayName);
			}

			@Override
			public void onFail(Exception e)
			{
				// handle failure
			}
		});

	}

	@Override
	public void render () {


		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();

		if(Gdx.input.justTouched()){

			String userId=GdxFIRAuth.instance().getCurrentUser().getUserInfo().getUid();
			GdxFIRDatabase.instance().inReference("scores/"+userId + "/user_score/").setValue(new UserScore("anonimo",23));

		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
