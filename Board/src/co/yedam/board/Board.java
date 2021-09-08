package co.yedam.board;	

public class Board {
		private int boardNum;
		private String boardTitle;
		private String boardContent;
		private String boardCreatedate;
		private String boardWriter;
		private int originNum;
		
		public int getBoardNum() {
			return boardNum;
		}
		public void setBoardNum(int boardNum) {
			this.boardNum = boardNum;
		}
		public String getBoardTitle() {
			return boardTitle;
		}
		public void setBoardTitle(String boardTitle) {
			this.boardTitle = boardTitle;
		}
		public String getBoardContent() {
			return boardContent;
		}
		public void setBoardContent(String boardContent) {
			this.boardContent = boardContent;
		}
		public String getBoardCreatedate() {
			return boardCreatedate;
		}
		public void setBoardCreatedate(String boardCreatedate) {
			this.boardCreatedate = boardCreatedate;
		}
		public String getBoardWriter() {
			return boardWriter;
		}
		public void setBoardWriter(String boardWriter) {
			this.boardWriter = boardWriter;
		}
		public int getOriginNum() {
			return originNum;
		}
		public void setOriginNum(int originNum) {
			this.originNum = originNum;
		}
	
		public void insertBoard(String boardNum2) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public String toString() {
			return "Board [boardNum=" + boardNum + ", boardTitle=" + boardTitle + ", boardContent=" + boardContent
					+ ", boardCreatedate=" + boardCreatedate + ", boardWriter=" + boardWriter + ", originNum="
					+ originNum + "]";
		}
		
		
}
