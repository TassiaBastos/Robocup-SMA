package jogadores;

import simple_soccer_lib.PlayerCommander;
import simple_soccer_lib.perception.PlayerPerception;
import simple_soccer_lib.utils.EFieldSide;
import simple_soccer_lib.utils.Vector2D;

public class MeioCampo extends BaseJogador {

	public MeioCampo(PlayerCommander player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	public void acaoArmador(long nextIteration, int pos) {
		double xInit = -30 + pos, yInit = 0;
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
				if (isBallPossession() && !isPointsAreClose(selfPerc.getPosition(), ballPos, 1)) {
					if (selfPerc.getUniformNumber() == 4 && getPlayerRecebendo() != 4 && getPlayerRecebendo() != -1)
						dash(new Vector2D(-10, 0), 70); 
					else if (selfPerc.getUniformNumber() == 5 && getPlayerRecebendo() != 5
							&& getPlayerRecebendo() != -1)
						dash(new Vector2D(10, 0), 70); 
				}
				if (isPointsAreClose(selfPerc.getPosition(), ballPos, 1)) {
					setBallPossession(true); 
					setPlayerRecebendo(-1); 					
					double dist1 = Vector2D.distance(selfPerc.getPosition(),
							fieldPerc.getTeamPlayer(selfPerc.getSide(), 6).getPosition());
					double dist2 = Vector2D.distance(selfPerc.getPosition(),
							fieldPerc.getTeamPlayer(selfPerc.getSide(), 7).getPosition());

					vTemp = dist1 > dist2 ? fieldPerc.getTeamPlayer(selfPerc.getSide(), 7).getPosition()
							: fieldPerc.getTeamPlayer(selfPerc.getSide(), 6).getPosition();
					if (dist1 > dist2) {
						setPlayerRecebendo(7);
						vTemp = fieldPerc.getTeamPlayer(selfPerc.getSide(), 7).getPosition();
					} else {
						setPlayerRecebendo(6);
						vTemp = fieldPerc.getTeamPlayer(selfPerc.getSide(), 6).getPosition();
					}
					turnToPoint(vTemp);
					double intensity = (vTemp.magnitude() * 100) / 40;
					kickToPoint(vTemp, intensity);
					setBallPossession(false);

				} else {
					pTemp = getClosestPlayerPoint(ballPos, side, 3, 0);
					if ((pTemp != null && (pTemp.getUniformNumber() == selfPerc.getUniformNumber())
							&& getPlayerRecebendo() == -1) || getPlayerRecebendo() == selfPerc.getUniformNumber()) {
						dash(ballPos, 100);
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
