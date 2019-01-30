package principal;

import jogadores.MeioCampo;
import jogadores.Atacante;
import jogadores.Zagueiro;
import jogadores.Goleiro;
import simple_soccer_lib.PlayerCommander;

public class ComandoJogador extends Thread {

	private int LOOP_INTERVAL = 100; // 0.1s
	private Goleiro goleiro;
	private Zagueiro defensorA;
	private Zagueiro defensorB;
	private MeioCampo meioA;
	private MeioCampo meioB;
	private Atacante atacanteA;
	private Atacante atacanteB;

	public ComandoJogador(PlayerCommander player) {
		goleiro = new Goleiro(player);
		defensorA = new Zagueiro(player);
		defensorB = new Zagueiro(player);
		meioA = new MeioCampo(player);
		meioB = new MeioCampo(player);
		atacanteA = new Atacante(player);
		atacanteB = new Atacante(player);
		
	}

	@Override
	public void run() {
		System.out.println(">> RODANDO... ");
		long nextIteration = System.currentTimeMillis() + LOOP_INTERVAL;
		goleiro.updatePerceptions();
		defensorA.updatePerceptions();
		defensorB.updatePerceptions();
		meioA.updatePerceptions();
		meioB.updatePerceptions();
		atacanteA.updatePerceptions();
		atacanteB.updatePerceptions();

		
		if (goleiro.selfPerc.getUniformNumber() == 1)
			goleiro.acaoGoleiro(nextIteration);
		if (defensorA.selfPerc.getUniformNumber() == 2)
			defensorA.acaoDefensor(nextIteration, -15);
		if (defensorB.selfPerc.getUniformNumber() == 3)
			defensorB.acaoDefensor(nextIteration, 15);
		if (meioA.selfPerc.getUniformNumber() == 4)
			meioA.acaoArmador(nextIteration, 0);
		if (meioB.selfPerc.getUniformNumber() == 5)
			meioB.acaoArmador(nextIteration, 15);
		if (atacanteA.selfPerc.getUniformNumber() == 6)
			atacanteA.acaoAtacante(nextIteration, 15);
		if (atacanteB.selfPerc.getUniformNumber() == 7)
			atacanteB.acaoAtacante(nextIteration, -15);

	}

}
