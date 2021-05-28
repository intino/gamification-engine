package io.intino.gamification.core;

import java.io.File;
import java.util.Collections;

public class Archetype {
	private final File root;

	public Archetype(File root) {
		this.root = root;
		root.mkdirs();
	}

	public File root() {
		return this.root;
	}

	public Relative relative() {
	    return new Relative(root);
	}

	private static File createParent(File file) {
	    file.getParentFile().mkdirs();
	    return file;
	}

	public Datahub datahub() {
		return new Datahub(root);
	}

	public Datamart datamart() {
		return new Datamart(root);
	}

	public static class Datahub {
		private final File root;

		public Datahub(File parent) {
			this.root = new File(parent, "datahub");
			root.mkdirs();
		}

		public File root() {
			return this.root;
		}

		public Broker broker() {
			return new Broker(root);
		}

		public Stage stage() {
			return new Stage(root);
		}

		public static class Broker {
			private final File root;

			public Broker(File parent) {
				this.root = new File(parent, "broker");
				root.mkdirs();
			}

			public File root() {
				return this.root;
			}
		}

		public static class Stage {
			private final File root;

			public Stage(File parent) {
				this.root = new File(parent, "stage");
				root.mkdirs();
			}

			public File root() {
				return this.root;
			}
		}
	}

	public static class Datamart {
		private final File root;

		public Datamart(File parent) {
			this.root = new File(parent, "datamarts");
			root.mkdirs();
		}

		public File root() {
			return this.root;
		}

		public Gamification gamification() {
			return new Gamification(root);
		}

		public Example example() {
			return new Example(root);
		}

		public static class Gamification {
			private final File root;

			public Gamification(File parent) {
				this.root = new File(parent, "gamification");
				root.mkdirs();
			}

			public File root() {
				return this.root;
			}
		}

		public static class Example {
			private final File root;

			public Example(File parent) {
				this.root = new File(parent, "example");
				root.mkdirs();
			}

			public File root() {
				return this.root;
			}
		}
	}

	public static class Relative {
        private final String name;
        private final File root;

        public Relative(File root) {
            this.name = "";
            this.root = root;
            root.mkdirs();
        }

        public String root() {
            return this.name;
        }

        public Datahub datahub() {
        	return new Datahub(root, name);
        }

        public Datamart datamart() {
        	return new Datamart(root, name);
        }

        public static class Datahub {
        	private final File root;
        	private final String name;

        	public Datahub(File parentRoot, String parent) {
        		this.root = new File(parent, "datahub");
        		root.mkdirs();
        		this.name = parent + (!parent.isEmpty() ? "/" : "") + "datahub";
        	}

        	public String root() {
        		return this.name;
        	}

        	public Broker broker() {
        		return new Broker(root, name);
        	}

        	public Stage stage() {
        		return new Stage(root, name);
        	}

        	public static class Broker {
        		private final File root;
        		private final String name;

        		public Broker(File parentRoot, String parent) {
        			this.root = new File(parent, "broker");
        			root.mkdirs();
        			this.name = parent + (!parent.isEmpty() ? "/" : "") + "broker";
        		}

        		public String root() {
        			return this.name;
        		}
        	}

        	public static class Stage {
        		private final File root;
        		private final String name;

        		public Stage(File parentRoot, String parent) {
        			this.root = new File(parent, "stage");
        			root.mkdirs();
        			this.name = parent + (!parent.isEmpty() ? "/" : "") + "stage";
        		}

        		public String root() {
        			return this.name;
        		}
        	}
        }

        public static class Datamart {
        	private final File root;
        	private final String name;

        	public Datamart(File parentRoot, String parent) {
        		this.root = new File(parent, "datamarts");
        		root.mkdirs();
        		this.name = parent + (!parent.isEmpty() ? "/" : "") + "datamarts";
        	}

        	public String root() {
        		return this.name;
        	}

        	public Gamification gamification() {
        		return new Gamification(root, name);
        	}

        	public Example example() {
        		return new Example(root, name);
        	}

        	public static class Gamification {
        		private final File root;
        		private final String name;

        		public Gamification(File parentRoot, String parent) {
        			this.root = new File(parent, "gamification");
        			root.mkdirs();
        			this.name = parent + (!parent.isEmpty() ? "/" : "") + "gamification";
        		}

        		public String root() {
        			return this.name;
        		}
        	}

        	public static class Example {
        		private final File root;
        		private final String name;

        		public Example(File parentRoot, String parent) {
        			this.root = new File(parent, "example");
        			root.mkdirs();
        			this.name = parent + (!parent.isEmpty() ? "/" : "") + "example";
        		}

        		public String root() {
        			return this.name;
        		}
        	}
        }
	}
}