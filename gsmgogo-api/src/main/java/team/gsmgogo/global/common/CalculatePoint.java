package team.gsmgogo.global.common;

public class CalculatePoint {
    public CalculatePointResponse execute(CalculatePointRequest req){
        CalculatePointResponse response = null;

        Long winTeamPoint = Math.max(req.getAllAPoint(), req.getAllBPoint());
        Long loseTeamPoint = Math.min(req.getAllAPoint(), req.getAllBPoint());
        Long allTeamPoint = winTeamPoint + loseTeamPoint;

        if(req.getAScore() == req.getBetAScore() && req.getBScore() == req.getBetBScore()){ 
            Long pointResult = (long) (req.getBetPoint() * (loseTeamPoint / winTeamPoint) + allTeamPoint * 1.5);
            response = new CalculatePointResponse(pointResult, null);
        }

        if((req.getAScore() > req.getBScore() && req.getBetAScore() > req.getBetBScore()) ||
        (req.getAScore() < req.getBScore() && req.getBetAScore() < req.getBetBScore())){
            Long pointResult = (long) (req.getBetPoint() * (loseTeamPoint / winTeamPoint) + allTeamPoint * 0.9);
            response = new CalculatePointResponse(pointResult, null);
        }

        if(response == null) {
            response = new CalculatePointResponse(null, req.getBetPoint());
        }

        return response;
    }
}

//나의 배팅액 * 패배 팀 총 배팅액 / 승리 팀 총 배팅액 + 배팅액) * 1.5
//나의 배팅액 * (패배 팀의 총 배팅액 / 승리 팀 총 배팅액) + 배팅액 * 0.9