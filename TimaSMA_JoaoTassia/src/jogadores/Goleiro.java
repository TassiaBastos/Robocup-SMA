package jogadores;

import java.awt.Rectangle;

import simple_soccer_lib.utils.EFieldSide;
import simple_soccer_lib.utils.Vector2D;

import simple_soccer_lib.PlayerCommander;

public class Goleiro extends BaseJogador {
	int localState = 0;
	Vector2D ballPos0, ballPos1; 

	public Goleiro(PlayerCommander player) {
		super(player);
	}

	public void acaoGoleiro(long nextIteration) {
		double xInit = -49, yInit = 0;
		EFieldSide side = selfPerc.getSide();
		Vector2D initPos = new Vector2D(xInit * side.value(), yInit * side.value());
		Vector2D goalPos = new Vector2D(50 * side.value(), 0);
		Vector2D ballPos;
		Rectangle area = side == EFieldSide.LEFT ? new Rectangle(-52, -20, 16, 40) : new Rectangle(36, -20, 16, 40);

		while (true) {
			ballPos0 = fieldPerc.getBall().getPosition();
			updatePerceptions();
			ballPos = fieldPerc.getBall().getPosition();
			switch (matchPerc.getState()) {
			case BEFORE_KICK_OFF:
				commander.doMoveBlocking(xInit * side.value(), yInit * side.value());
				break;

			case OFFSIDE_LEFT:
				break;
			case OFFSIDE_RIGHT:
				break;
			case KICK_OFF_LEFT:
				dash(new Vector2D(xInit * side.value(), ballPos.getY() / 5), 70);
				break;
			case KICK_OFF_RIGHT:
				dash(new Vector2D(xInit * side.value(), ballPos.getY() / 5), 70);
				break;
			case FREE_KICK_LEFT:
				if(isPointsAreClose(selfPerc.getPosition(), ballPos, 1)) {
					turnToPoint(goalPos);
					kickToPoint(goalPos, 85);
				}
				break;
			case FREE_KICK_RIGHT:
				break;
			case KICK_IN_LEFT:
				break;
			case KICK_IN_RIGHT:
				break;
			case GOAL_KICK_LEFT:
				if (side == EFieldSide.LEFT) {
					commander.doMoveBlocking(ballPos.getX(), ballPos.getY());
					ballPos = fieldPerc.getBall().getPosition();
					dash(ballPos, 100);
					if(isPointsAreClose(selfPerc.getPosition(), ballPos, 1)) {
						turnToPoint(goalPos);
						kickToPoint(goalPos, 85);
					}
				}
				break;
			case GOAL_KICK_RIGHT:
				if (side == EFieldSide.RIGHT) {
					commander.doMoveBlocking(ballPos.getX(), ballPos.getY());
					turnToPoint(goalPos);
					kickToPoint(goalPos, 85);
				}
				break;
			case PLAY_ON:

				switch (localState) {

				case 0: 
					System.out.println("Goleiro: " + localState + selfPerc.getTeam());
					ballPos1 = ballPos;
					
					
					if (area.contains(ballPos.getX(), ballPos.getY())) 
						localState = 3;

					if ((side == EFieldSide.LEFT && ballPos.getX() < 0)
							|| (side == EFieldSide.RIGHT && ballPos.getX() > 0)){
						double coefAng = (ballPos1.getY() - ballPos0.getY()) / (ballPos1.getX() - ballPos0.getX());

						double y;
						y = ballPos1.getY() + (coefAng * ((-50 * side.value()) - ballPos.getX()));
						if (Double.isNaN(y) || Double.isInfinite(y))
							y = 0;
						Vector2D vect = new Vector2D(-50 * side.value(), y);
						if (vect.getY() <= 7 && vect.getY() >= -7) { 
							dash(vect, 70);
						}
						// TODO 
						if(isPointsAreClose(selfPerc.getPosition(), vect, 1.2))
							localState = 1;
					} else if (isPointsAreClose(selfPerc.getPosition(), ballPos, 1.2)) 
						localState = 1; 
					break;

				case 1: 
					System.out.println("Goleiro: " + localState + selfPerc.getTeam());
					
					
					commander.doCatchBlocking(0);
					kickToPoint(goalPos, 1);
					if (isPointsAreClose(selfPerc.getPosition(), ballPos, 1.2))
						localState = 2;
					else
						localState = 0;
					break;

				case 2: 
					System.out.println("Goleiro: " + localState);
					if (isPointsAreClose(selfPerc.getPosition(), ballPos, 2))
						kickToPoint(goalPos, 85);
					//}
					else
						localState = 3;
					localState = 0;
					break;

				case 3: 
					System.out.println("Goleiro: " + localState);
					if (area.contains(ballPos.getX(), ballPos.getY())) {
						dash(ballPos, 100);
						if (isPointsAreClose(selfPerc.getPosition(), ballPos, 0.5)) {
							kickToPoint(goalPos, 100);
						}
					} else {
						dash(initPos, 100);
						localState = 0;
					}
					break;

				}
				
			default:
				turnToPoint(ballPos);
				break;
			}
		}
	}
}
