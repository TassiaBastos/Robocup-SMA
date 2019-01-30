package jogadores;

import simple_soccer_lib.PlayerCommander;
import simple_soccer_lib.perception.PlayerPerception;
import simple_soccer_lib.utils.EFieldSide;
import simple_soccer_lib.utils.Vector2D;

public class Atacante extends BaseJogador {

	public Atacante(PlayerCommander player) {
		super(player);
		
	}

	public void acaoAtacante(long nextIteration, int pos) {
		double xInit = -15, yInit = 0 + pos;
		EFieldSide side = selfPerc.getSide();
		Vector2D initPos = new Vector2D(xInit * side.value(), yInit);
		Vector2D goalPos = new Vector2D(50 * side.value(), 0);
		Vector2D ballPos;
		PlayerPerception pTemp;
		while (true) {
			updatePerceptions();
			ballPos = fieldPerc.getBall().getPosition();
			switch (matchPerc.getState()) {
			
			case OFFSIDE_LEFT:
				if (side == EFieldSide.LEFT){

				}
				break;
			case OFFSIDE_RIGHT:
				if (side == EFieldSide.RIGHT){

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
			case GOAL_KICK_LEFT:
				if (side == EFieldSide.LEFT){

				}
				break;
			case GOAL_KICK_RIGHT: 
				if (side == EFieldSide.RIGHT){

				}
				break;
			
			case BEFORE_KICK_OFF:
				commander.doMoveBlocking(xInit * side.value(), yInit * side.value());
				break;
			case KICK_OFF_LEFT: setPlayerRecebendo(-1);
			case KICK_OFF_RIGHT: setPlayerRecebendo(-1);
			case PLAY_ON:
				if (isBallPossession() && !isPointsAreClose(selfPerc.getPosition(), ballPos, 1)) {
					if (selfPerc.getUniformNumber() == 7 && getPlayerRecebendo() != 7 && getPlayerRecebendo() != -1)
						dash(new Vector2D(ballPos.getX() + 10 * side.value(), -15), 70);
					else if (selfPerc.getUniformNumber() == 6 && getPlayerRecebendo() != 6
							&& getPlayerRecebendo() != -1)
						dash(new Vector2D(goalPos.getX() - 20 * side.value(), 15), 70);
				}
				if (isPointsAreClose(selfPerc.getPosition(), ballPos, 1)) { 
					setBallPossession(true); 
					setPlayerRecebendo(-1);
					if (isPointsAreClose(ballPos, goalPos, 25)) {
						kickToPoint(goalPos, 100); 
						setBallPossession(false);
					} else {
						PlayerPerception p = getClosestPlayerPoint(goalPos, selfPerc.getSide(), 2, 0);
						if (p.getUniformNumber() != selfPerc.getUniformNumber()) {
							Vector2D posTemp = p.getPosition();
							double distance = selfPerc.getPosition().distanceTo(posTemp);
							if (!isOffside(posTemp, fieldPerc.getTeamPlayers(EFieldSide.invert(side)), side) && distance <= 20) {
								turnToPoint(posTemp);
								double intensity = (posTemp.magnitude() * 100) / 20;
								
								setPlayerRecebendo(p.getUniformNumber());
								kickToPoint(posTemp, intensity);
							} else {
								setBallPossession(true);
								kickToPoint(goalPos, 15); 
								
							}
						} else {
							setBallPossession(true);
							kickToPoint(goalPos, 15); 
						}
					}
				} else {
					pTemp = getClosestPlayerPoint(ballPos, side, 3, 0);
					if ((pTemp != null && pTemp.getUniformNumber() == selfPerc.getUniformNumber()
							&& getPlayerRecebendo() == -1) || getPlayerRecebendo() == selfPerc.getUniformNumber()) {
						dash(ballPos, 95);
					} else if (!isPointsAreClose(selfPerc.getPosition(), initPos, 3)) {
					} else {
						turnToPoint(ballPos);
					}
				}
				break;
			default:
				break;
			}
		}
	}

}
