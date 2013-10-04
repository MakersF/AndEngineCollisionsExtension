package com.makersf.andengine.extension.collisions;

import android.os.SystemClock;
import android.util.Log;

/**
 * 
 * @author Francesco Zoffoli
 * @since 01.08.2012
 *
 */
public class CollisionLogger {

	private final String mName;
	private long mCollisionStartTime;

	private long mTotalSuccesfullCollisionTime;
	private int mSuccesfullCollisions;
	private long mMinSuccesfullTime = Long.MAX_VALUE;
	private long mMaxSuccesfullTime;
	
	private long mTotalUnsuccesfullCollisionTime;
	private int mUnsuccesfullCollisions;
	private long mMinUnsuccesfullTime = Long.MAX_VALUE;
	private long mMaxUnsuccesfullTime;

	public CollisionLogger(String pName) {
		mName = pName;
	}

	private long getCurrentTime() {
		return SystemClock.elapsedRealtime();
	}
	
	public void startCollisionCheck() {
		mCollisionStartTime = getCurrentTime();
	}
	
	public void endCollisionCheck(boolean pCollisionSuccesfull) {
		final long currentTime = getCurrentTime();
		final long elapsedTime = currentTime - mCollisionStartTime;
		if(pCollisionSuccesfull) {
			mTotalSuccesfullCollisionTime += elapsedTime;
			mSuccesfullCollisions++;
			
			mMinSuccesfullTime = Math.min(mMinSuccesfullTime, elapsedTime);
			mMaxSuccesfullTime = Math.max(mMaxSuccesfullTime, elapsedTime);
		}
		else {
			mTotalUnsuccesfullCollisionTime += elapsedTime;
			mUnsuccesfullCollisions++;
			
			mMinUnsuccesfullTime = Math.min(mMinUnsuccesfullTime, elapsedTime);
			mMaxUnsuccesfullTime = Math.max(mMaxUnsuccesfullTime, elapsedTime);
		}
	}
	
	public void printStatistics() {
		Log.i(mName, (mSuccesfullCollisions + mUnsuccesfullCollisions) + " in " + (mTotalSuccesfullCollisionTime + mTotalUnsuccesfullCollisionTime) + " ms, Avarage: " + (((float) mTotalSuccesfullCollisionTime + (float) mTotalUnsuccesfullCollisionTime)/((float) mSuccesfullCollisions + (float) mUnsuccesfullCollisions)) + " ms. \n" +
				mSuccesfullCollisions + " succesfull collisions in " + mTotalSuccesfullCollisionTime + " ms. Min: " + mMinSuccesfullTime + ", Max: " + mMaxSuccesfullTime + ", Avarage: " + ((float) mTotalSuccesfullCollisionTime)/((float) mSuccesfullCollisions) + ".\n" +
				mUnsuccesfullCollisions + " unsuccesfull collisions in " + mTotalUnsuccesfullCollisionTime + " ms. Min: " + mMinUnsuccesfullTime + ", Max: " + mMaxUnsuccesfullTime + ", Avarage: " + ((float) mTotalUnsuccesfullCollisionTime)/((float) mUnsuccesfullCollisions) + ".");
	}

	public void reset() {
		mTotalSuccesfullCollisionTime = 0;
		mSuccesfullCollisions = 0;
		mMinSuccesfullTime = Long.MAX_VALUE;
		mMaxSuccesfullTime = 0;

		mTotalUnsuccesfullCollisionTime = 0;
		mUnsuccesfullCollisions = 0;
		mMinUnsuccesfullTime = Long.MAX_VALUE;
		mMaxUnsuccesfullTime = 0;
	}
}
