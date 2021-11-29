package cn.neu.aimp.iot.constant.lib.enumeration;

/**
 * @author 47081
 * @version 1.0
 * @description 语音类型
 * @date 2021/2/20
 */
public enum EM_CUSTOM_EDUCATION_VOICE_TYPE {
  /** 未知 */
  EM_CUSTOM_EDUCATION_VOICE_TYPE_UNKNOWN,
  /** 超时出校 */
  EM_CUSTOM_EDUCATION_VOICE_TYPE_LEAVE_SCHOOL_TIMEOUT,
  /** 超时进校 */
  EM_CUSTOM_EDUCATION_VOICE_TYPE_ENTER_SCHOOL_TIMEOUT,
  /** 出校 */
  EM_CUSTOM_EDUCATION_VOICE_TYPE_LEAVE_SCHOOL,
  /** 此卡未绑定 */
  EM_CUSTOM_EDUCATION_VOICE_TYPE_CARD_NOT_BIND,
  /** 寄宿生 */
  EM_CUSTOM_EDUCATION_VOICE_TYPE_BOARDER,
  /** 家长卡已挂失 */
  EM_CUSTOM_EDUCATION_VOICE_TYPE_PARENT_CARD_REPORT_LOST,
  /** 进校已过期 */
  EM_CUSTOM_EDUCATION_VOICE_TYPE_ENTER_SCHOOL_OVERDUE,
  /** 您有包裹待领取 */
  EM_CUSTOM_EDUCATION_VOICE_TYPE_PACKAGE_TO_PICKUP,
  /** 请假 */
  EM_CUSTOM_EDUCATION_VOICE_TYPE_ASKFORLEAVE,
  /** 请假返校超时 */
  EM_CUSTOM_EDUCATION_VOICE_TYPE_RETURN_SCHOOL_TIMEOUT_WHEN_ASKFORLEAVE,
  /** 请假进出校时间未到 */
  EM_CUSTOM_EDUCATION_VOICE_TYPE_INANDOUT_SCHOOL_TIMENOTUP_WHEN_ASKFORLEAVE,
  /** 请假拒绝出校 */
  EM_CUSTOM_EDUCATION_VOICE_TYPE_REFUSE_LEAVE_SCHOOL_WHEN_ASKFORLEAVE,
  /** 请假拒绝进校 */
  EM_CUSTOM_EDUCATION_VOICE_TYPE_REFUSE_ENTER_SCHOOL_WHEN_ASKFORLEAVE,
  /** 请假审核中 */
  EM_CUSTOM_EDUCATION_VOICE_TYPE_ASKFORLEAVE_IN_REVIEW,
  /** 请假已过期 */
  EM_CUSTOM_EDUCATION_VOICE_TYPE_ASKFORLEAVE_EXPIRED,
  /** 请假已批准 */
  EM_CUSTOM_EDUCATION_VOICE_TYPE_ASKFORLEAVE_APPROVED,
  /** 请假已失效禁止重复出校 */
  EM_CUSTOM_EDUCATION_VOICE_TYPE_FORBID_LEAVE_SCHOOL_WITH_LEAVE_INVALID,
  /** 时间未到 */
  EM_CUSTOM_EDUCATION_VOICE_TYPE_TIME_ISNOT_UP,
  /** 未预约 */
  EM_CUSTOM_EDUCATION_VOICE_TYPE_NOT_APPOINT,
  /** 未在允许时段内禁止通行 */
  EM_CUSTOM_EDUCATION_VOICE_TYPE_NO_PASSAGE_IN_NONPERMIT_TIMESECTION,
  /** 无效卡 */
  EM_CUSTOM_EDUCATION_VOICE_TYPE_INVALID_CARD,
  /** 已预约 */
  EM_CUSTOM_EDUCATION_VOICE_TYPE_ALREADY_APPOINTED,
  /** 允许返校 */
  EM_CUSTOM_EDUCATION_VOICE_TYPE_ALLOW_BACK_SCHOOL,
  /** 再见 */
  EM_CUSTOM_EDUCATION_VOICE_TYPE_GOODBYE,
  /** 正常进校 */
  EM_CUSTOM_EDUCATION_VOICE_TYPE_ENTER_SCHOOL_NORMALLY,
  /** 重复出校 */
  EM_CUSTOM_EDUCATION_VOICE_TYPE_REPEAT_LEAVE_SCHOOLL,
  /** 重复进校 */
  EM_CUSTOM_EDUCATION_VOICE_TYPE_REPEAT_ENTER_SCHOOLL,
  /** 走读生 */
  EM_CUSTOM_EDUCATION_VOICE_TYPE_DAY_STUDENT;

  public static EM_CUSTOM_EDUCATION_VOICE_TYPE getEducationVoiceType(int type) {
    for (EM_CUSTOM_EDUCATION_VOICE_TYPE educationVoiceType :
        EM_CUSTOM_EDUCATION_VOICE_TYPE.values()) {
      if (educationVoiceType.ordinal() == type) {
        return educationVoiceType;
      }
    }
    return EM_CUSTOM_EDUCATION_VOICE_TYPE_UNKNOWN;
  }
}
