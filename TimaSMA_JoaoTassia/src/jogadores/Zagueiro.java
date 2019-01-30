package jogadores;

import simple_soccer_lib.PlayerCommander;
import simple_soccer_lib.perception.PlayerPerception;
import simple_soccer_lib.utils.EFieldSide;
import simple_soccer_lib.utils.Vector2D;

public class Zagueiro extends BaseJogador {

	public Zagueiro(PlayerCommander player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	public void acaoDefensor(long nextIteration, int pos) {
		double xInit = -30, yInit = 0 + pos;
		EFieldSide side = selfPerc.getSide();
		Vector2D initPos = new Vector2D(xInit * side.value(), yInit * side.value());
		Vector2D ballPos, vTemp;
		PlayerPerception pTemp;

		while (true) {
			updatePerceptions();
			ballPos = fieldPerc.getBall().getPosition();
			switch (matchPerc.getState()) {

			case OFFSIDE_LEFT:
				if (side == EFieldSide.LEFT){
					dinamica(ballPos, side);
				}
				break;
			case OFFSIDE_RIGHT:
				if (side == EFieldSide.RIGHT){
					dinamica(ballPos, side);
				}
				break;
			case FREE_KICK_LEFT: 
				if (side == EFieldSide.LEFT){
					dinamica(ballPos, side);
				}
				break;
			case FREE_KICK_RIGHT:
				if (side == EFieldSide.RIGHT){
					dinamica(ballPos, side);
				}
				break;
			case KICK_IN_LEFT: 
				if (side == EFieldSide.LEFT){
					dinamica(ballPos, side);
				}
				break;
			case KICK_IN_RIGHT: 
				if (side == EFieldSide.RIGHT){
					dinamica(ballPos, side);
				}
				break;
			case GOAL_KICK_LEFT:
				if (side == EFieldSide.LEFT){

				}
				break;
			case GOAL_KICK_RIGHT: 
				if (side == EFieldSide.RIGHT){

				}
				break;
			case CORNER_KICK_LEFT:
				if (side == EFieldSide.LEFT){
					dinamica(ballPos, side);
				}
				break;
			case CORNER_KICK_RIGHT:
				if (side == EFieldSide.RIGHT){
					dinamica(ballPos, side);
				}
				break;
			case BEFORE_KICK_OFF:
				commander.doMoveBlocking(xInit * side.value(), yInit * side.value());
				break;
			case KICK_OFF_LEFT: setPlayerRecebendo(-1);
			case KICK_OFF_RIGHT: setPlayerRecebendo(-1);
			case PLAY_ON:
				
				if (isBallPossession()) { 
					if (isPointsAreClose(selfPerc.getPosition(), ballPos, 1)) {
						setBallPossession(true);
						if (selfPerc.getUniformNumber() == 2) {
							vTemp = fieldPerc.getTeamPlayer(side, 3).getPosition();
						} else {
							vTemp = fieldPerc.getTeamPlayer(side, 4).getPosition();
						}
						Vector2D vTempF = vTemp.sub(selfPerc.getPosition());
						double intensity = (vTempF.magnitude() * 100) / 40;
						kickToPoint(vTemp, intensity);
						setBallPossession(false);
					} else {
						pTemp = getClosestPlayerPoint(ballPos, side, 3, 0);
						if (pTemp != null && pTemp.getUniformNumber() == selfPerc.getUniformNumber()) {
							dash(ballPos, 85);
						} else if (!isPointsAreClose(selfPerc.getPosition(), initPos, 3)) {
							dash(initPos, 70);
						} else {
							turnToPoint(ballPos);
						}
					}
				} else {
					if (isPointsAreClose(selfPerc.getPosition(), ballPos, 12)) {
						dash(ballPos, 100);
						if (isPointsAreClose(selfPerc.getPosition(), ballPos, 2) )
							kickToPoint(new Vector2D(50 * side.value(), 0), 50);
					}
				}
				break;
			default:
				break;
			}
		}

	}
}
