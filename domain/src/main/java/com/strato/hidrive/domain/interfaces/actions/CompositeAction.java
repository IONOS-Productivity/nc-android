package com.strato.hidrive.domain.interfaces.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yaz on 7/12/16.
 */

public class CompositeAction implements Action {

	private final List<Action> actions = new ArrayList<>();

	public CompositeAction(Action... actions) {
		Collections.addAll(this.actions, actions);
	}

	@Override
	public void execute() {
		for (Action action : this.actions) {
			action.execute();
		}
	}
}
