package berlinclock;


public class BerlinClockConverter implements TimeConverter{    

    public String convertTime(String time) {
        if(!time.matches("[012]\\d:[0123456]\\d:[0123456]\\d")){
            return "";
        }
        String unitOfTime[] = time.split(":");
        int hour = Integer.parseInt(unitOfTime[0]);
        int minute = Integer.parseInt(unitOfTime[1]);
        int seconds = Integer.parseInt(unitOfTime[2]);
        return getSecondsRow(seconds) + getFirstHourRow(hour) + getSecondHourOrMinuteRow(hour, true) + getFirstMinuteRow(minute) + getSecondHourOrMinuteRow(minute, false);
    }
    
    private String getSecondsRow(int seconds){
        if((seconds&1)==1){
            return "O\n";
        }
        else{
            return "Y\n";
        }
    }
    
    private String getFirstHourRow(int hour){
        String convertTime = "";
        for (int i = 0; i < 4; i++) {
            if((hour-(i+1)*5)>=0){
                convertTime += "R";
            }
            else{
                convertTime += "O";
            }
        }
        return convertTime+"\n";
    }
    
    private String getSecondHourOrMinuteRow(int hour, boolean unitOfTime){
        String convertTime = "";
        String colorOfLamp = "";
        if(unitOfTime){
            colorOfLamp = "R";
        }
        else{
            colorOfLamp = "Y";
        }        
        for (int i = 0; i < hour%5; i++) {
            convertTime += colorOfLamp;
        }
        for (int i = 0; i < 4-hour%5; i++) {
            convertTime += "O";
        }
        if(unitOfTime){
            return convertTime+"\n";
        }
        else{
            return convertTime;
        }   
        
    }
    
    private String getFirstMinuteRow(int minute){
        String convertTime = "";
        for (int i = 0; i < 11; i++) {
            if((minute-(i+1)*5)>=0){
                if(i==2 || i==5 || i==8){
                    convertTime += "R";
                }
                else{
                    convertTime += "Y";
                }
            }
            else{
                convertTime += "O";
            }
        }
        return convertTime+"\n";
    }
    
}