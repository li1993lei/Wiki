//声明一个手势识别器 
private GestureDetector gd;
//初始化一个手势识别器
	gd = new GestureDetector(new OnGestureListener() {
			
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void onShowPress(MotionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
					float distanceY) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void onLongPress(MotionEvent e) {
				// TODO Auto-generated method stub
				
			}
			/*
				e1手指按下去的点   e2手指抬起的点 
				velocityX  水平方向上的速度
				velocityY   数值数值方向上的速度
			*/
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
					float velocityY) {
				// TODO Auto-generated method stub
				if((e2.getRawX() - e1.getRawX()) > 200){
					//显示上一个页面：从左向右滑动
					prev(null);  //在这里做页面切换的逻辑
					return true;
				}
				if((e1.getRawX() - e2.getRawX()) > 200){
					//显示下一个页面：从右向左滑动
					next(null);
					System.out.println("显示下一个页面");
					return true;
				}
				return true;
			}
			
			@Override
			public boolean onDown(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}
		});
//要想让手势识别器生效 恤绑定onTouch事件
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		gd.onTouchEvent(event);//绑定onTouch事件
		return super.onTouchEvent(event);
	}


	public void next(View view){
		//进入到下个设置界面
		nextActivity();
		//动画的效果
		nextAnimation();
	}
	private void nextAnimation() {
		// TODO Auto-generated method stub
		overridePendingTransition(R.anim.next_in, R.anim.next_out);
	}
	public void prev(View view){
		//进入到下个设置界面
		preActivity();
		//上的动画效果
		overridePendingTransition(R.anim.pre_in, R.anim.pre_out);
	}