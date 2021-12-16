package BusinessLogic;

public class Review {

    private int reviewGrade;
    private String reviewComment;


    public Review(int grade) {                    // include int grade in the constructor but leave comment optional
        this.reviewGrade = grade;
    }

    public Review(int reviewGrade, String reviewComment){
        this.reviewGrade = reviewGrade;
        this.reviewComment = reviewComment;
    }


    public String getReviewComment(){
        return this.reviewComment;
    }

    public int getReviewGrade(){
        return this.reviewGrade;
    }


    @Override
    public String toString() {
    String result = "";
        if (this.reviewComment == null){
            result = "Grade: " + this.reviewGrade + ".";
        } else {
            result = "Grade: " + this.reviewGrade + "." + this.reviewComment;
        }
        return result;
    }
}
