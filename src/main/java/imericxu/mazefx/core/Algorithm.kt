package imericxu.mazefx.core

abstract class Algorithm(val nodes: Array<Node>) {
	abstract val states: Array<State>
	val changeList: MutableList<Int> = mutableListOf()
	var isFinished = false
		private set

	open fun loopOnce() {
		assert(!isFinished)
		isFinished = loopOnceImpl()
	}

	/**
	 * Calls [loopOnce] until the algorithm is complete
	 */
	fun finishImmediately() {
		do {
			loopOnce()
			changeList.clear()
		} while (!isFinished)
	}

	protected abstract fun loopOnceImpl(): Boolean

	/**
	 * Changes the state of the node at [id] to [state] and adds the node to the [changeList]
	 */
	protected fun changeState(id: Int, state: State) {
		states[id] = state
		changeList.add(id)
	}
}
