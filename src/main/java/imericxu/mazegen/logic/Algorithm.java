package imericxu.mazegen.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class Algorithm {
	public final List<Integer> changeList = new ArrayList<>();
	protected final Node[] nodes;
	protected final State[] states;
	protected final Random rand = new Random();
	protected boolean finished;

	public Algorithm(Node[] nodes, State[] states) {
		this.nodes = nodes;
		this.states = states;
	}

	public void loopOnce() {
		assert !finished;
		finished = loopOnceImpl();
	}

	public void instantSolve() {
		do {
			changeList.clear();
			loopOnce();
		} while (!isFinished());
	}

	public boolean isFinished() {
		return finished;
	}

	public Set<Integer> getConnectionsOf(int id) {
		return nodes[id].getConnections();
	}

	public Set<Integer> getNeighborsOf(int id) {
		return nodes[id].getNeighbors();
	}

	public State getState(int id) {
		return states[id];
	}

	public Node[] getNodesCopy() {
		return nodes.clone();
	}

	public State[] getStatesCopy() {
		return states.clone();
	}

	/**
	 * @return whether or not the algorithm is finished
	 */
	protected abstract boolean loopOnceImpl();

	protected void changeState(int id, State state) {
		states[id] = state;
		changeList.add(id);
	}

	protected void changeState(Node node, State state) {
		states[node.id] = state;
		changeList.add(node.id);
	}
}
