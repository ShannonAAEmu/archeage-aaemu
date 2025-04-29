<div id="header" align="center">
  <img alt="AAEMU" src="https://i.imgur.com/NFDY376.png" width="300">
  <br>
  <div id="badges">
   <a href="Discord">
     <img alt="Discord" src="https://img.shields.io/discord/479677351618281472?color=%235865F2&label=Discord&logo=Discord&logoColor=%23FFFFFF">
   </a>
 </div>
</div>

# - ArcheAge - Apr 23 2013 (12:15:40)

## Overview

The project is written for educational purposes only and cannot be used commercially.

All rights to the game client belong to its developers.

You can make any changes to the game client at your own risk.
You bare responsibility for the consequences.

---

| Server | Type                          |
|--------|-------------------------------|
| Auth   | [ClientAuth](#ClientAuth)     |
| Auth   | [AuthClient](#AuthClient)     |
| Stream | [ClientStream](#ClientStream) |
| Stream | [StreamClient](#StreamClient) |
| Game   | [Proxy](#Proxy)               |
| Game   | [ClientServer](#ClientServer) |
| Game   | [ServerClient](#ServerClient) |
| Editor | [ClientEditor](#ClientEditor) |
| Editor | [EditorClient](#EditorClient) |
| Zone   | [ZoneWorld](#ZoneWorld)       |
| Zone   | [WorldZone](#WorldZone)       |

## Packets

The minus sign `-` in the **Name** column means that there is no packet for the given opcode.

The <span style='color: red'>red</span> font color is used for the packets that are either:

- GM
- Debug

The total number of packets: 933:

- In progress 2
- Done 49
- Skipped 3
- Not done 879

---

### ClientAuth

| Opcode | Name                 | Status  |
|--------|----------------------|---------|
| 000    | -                    | -       |
| 001    | CARequestAuth        | Done    |
| 002    | CARequestAuthTencent | Skipped |
| 003    | CAChallengeResponse  | Done    |
| 004    | CAChallengeResponse2 | Skipped |
| 005    | CAOtpNumber          | Done    |
| 006    | CATestArs            | Done    |
| 007    | CAPcCertNumber       | Done    |
| 008    | CAListWorld          | Done    |
| 009    | CAEnterWorld         | Done    |
| 00A    | CACancelEnterWorld   | Done    |
| 00B    | CARequestReconnect   | Done    |

### AuthClient

| Opcode | Name               | Status  |
|--------|--------------------|---------|
| 000    | ACJoinResponse     | Done    |
| 001    | -                  | -       |
| 002    | ACChallenge        | Done    |
| 003    | ACAuthResponse     | Done    |
| 004    | ACChallenge2       | Skipped |
| 005    | ACEnterOtp         | Done    |
| 006    | ACShowArs          | Done    |
| 007    | ACEnterPcCert      | Done    |
| 008    | ACWorldList        | Done    |
| 009    | ACWorldQueue       | Done    |
| 00A    | ACWorldCookie      | Done    |
| 00B    | ACEnterWorldDenied | Done    |
| 00C    | ACLoginDenied      | Done    |
| 00D    | ACAccountWarned    | Done    |

### ClientStream

| Opcode | Name                         | Status   |
|--------|------------------------------|----------|
| 000    | -                            | -        |
| 001    | CTJoin                       | Done     |
| 002    | CTRequestCell                | Not done |
| 003    | CTRequestEmblem              | Not done |
| 004    | CTCancelCell                 | Not done |
| 005    | CTContinue                   | Not done |
| 006    | CTUccComplex                 | Not done |
| 007    | CTUCcString                  | Not done |
| 008    | CTUccPosition                | Not done |
| 009    | CTUccCharacterName           | Not done |
| 00A    | CTQueryCharName              | Not done |
| 00B    | -                            | -        |
| 00C    | CTUploadEmblemStream         | Not done |
| 00D    | CTEmblemStreamUploadStatus   | Not done |
| 00E    | CTStartUploadEmblemStream    | Not done |
| 00F    | CTEmblemStreamDownloadStatus | Not done |
| 010    | CTItemUcc                    | Not done |
| 011    | CTEmblemPartDownloaded       | Not done |
| 012    | CTUccComplexCheckValid       | Not done |

### StreamClient

| Opcode | Name                     | Status   |
|--------|--------------------------|----------|
| 000    | -                        | -        |
| 001    | TCJoinResponse           | Done     |
| 002    | TCDoodadStream           | Not done |
| 003    | TCDoodadIds              | Not done |
| 004    | TCDownloadEmblem         | Not done |
| 005    | TCUccComplex             | Not done |
| 006    | TCUccString              | Not done |
| 007    | TCUccPosition            | Not done |
| 008    | TCUccCharName            | Not done |
| 009    | TCCharNameQueried        | Not done |
| 00A    | TCEmblemStreamRecvStatus | Not done |
| 00B    | TCEmblemStreamSendStatus | Not done |
| 00C    | TCEmblemStreamDownload   | Not done |
| 00D    | TCItemUccData            | Not done |
| 00E    | TCHouseFarm              | Not done |
| 00F    | TCUccComplexCheckValid   | Not done |

### Proxy

| Level | Opcode | Name                    | Status   |
|-------|--------|-------------------------|----------|
| 02    | 000    | ChangeState             | Done     |
| 02    | 000    | X2EnterWorld            | ?        |
| 02    | 000    | X2EnterWorldResponse    | ?        |
| 02    | 001    | FinishState             | Done     |
| 02    | 002    | FlushMsgs               | Not done |
| 02    | 004    | UpdatePhysicsTime       | Not done |
| 02    | 005    | BeginUpdateObj          | Not done |
| 02    | 006    | EndUpdateObj            | Not done |
| 02    | 007    | BeginBindObj            | Not done |
| 02    | 008    | EndBindObj              | Not done |
| 02    | 009    | UnbindPredictedObj      | Not done |
| 02    | 00A    | RemoveStaticObj         | Not done |
| 02    | 00B    | VoiceData               | Not done |
| 02    | 00C    | UpdateAspect            | Not done |
| 02    | 00D    | SetAspectProfile        | Not done |
| 02    | 00E    | PartialAspect           | Not done |
| 02    | 00F    | SetGameType             | Done     |
| 02    | 010    | ChangeCVar              | Not done |
| 02    | 011    | EntityClassRegistration | Not done |
| 02    | 012    | Ping                    | Done     |
| 02    | 013    | Pong                    | Done     |
| 02    | 014    | SeqChange               | Not done |

### ClientServer

| Level | Opcode | Name                                                  | Status      |
|-------|--------|-------------------------------------------------------|-------------|
| 01    | 000    | X2EnterWorld                                          | Done        |
| 01    | 001    | CSLeaveWorld                                          | Done        |
| 01    | 002    | CSCancelLeaveWorld                                    | Not done    |
| 01    | 003    | CSReloadFactionRelations                              | Not done    |
| 01    | 004    | CSCreateExpedition                                    | Not done    |
| 01    | 005    | CSChangeExpeditionSponsor                             | Not done    |
| 01    | 006    | CSChangeExpeditionRolePolicy                          | Not done    |
| 01    | 007    | CSChangeExpeditionMemberRole                          | Not done    |
| 01    | 008    | CSChangeExpeditionOwner                               | Not done    |
| 01    | 009    | -                                                     | -           |
| 01    | 00A    | CSDismissExpedition                                   | Not done    |
| 01    | 00B    | CSInviteToExpedition                                  | Not done    |
| 01    | 00C    | CSReplyExpeditionInvitation                           | Not done    |
| 01    | 00D    | CSLeaveExpedition                                     | Not done    |
| 01    | 00E    | CSKickFromExpedition                                  | Not done    |
| 01    | 00F    | -                                                     | -           |
| 01    | 010    | CSUpdateDominionTaxRate                               | Not done    |
| 01    | 011    | CSFactionImmigrate                                    | Not done    |
| 01    | 012    | CSRequestDominionData                                 | Not done    |
| 01    | 013    | CSFamilyInviteMember                                  | Not done    |
| 01    | 014    | CSFamilyReplyInvitation                               | Not done    |
| 01    | 015    | CSFamilyLeave                                         | Not done    |
| 01    | 016    | CSFamilyKick                                          | Not done    |
| 01    | 017    | CSFamilyChangeTitle                                   | Not done    |
| 01    | 018    | CSFamilyChangeOwner                                   | Not done    |
| 01    | 019    | CSListCharacter                                       | Done        |
| 01    | 01A    | CSRefreshInCharacterList                              | Done        |
| 01    | 01B    | CSCreateCharacter                                     | Done        |
| 01    | 01C    | CSEditCharacter                                       | Not done    |
| 01    | 01D    | CSDeleteCharacter                                     | In progress |
| 01    | 01E    | CSSelectCharacter                                     | Not done    |
| 01    | 01F    | CSSpawnCharacter                                      | Not done    |
| 01    | 020    | CSCancelCharacterDelete                               | Not done    |
| 01    | 021    | CSNotifyInGame                                        | Not done    |
| 01    | 022    | CSNotifyInGameCompleted                               | Not done    |
| 01    | 023    | <span style='color: red'>CSEditorGameMode</span>      | Not done    |
| 01    | 024    | CSChangeTarget                                        | Not done    |
| 01    | 025    | CSRequestCharBrief                                    | Not done    |
| 01    | 026    | CSSpawnSlave                                          | Not done    |
| 01    | 027    | CSDespawnSlave                                        | Not done    |
| 01    | 028    | CSDestroySlave                                        | Not done    |
| 01    | 029    | CSBindSlave                                           | Not done    |
| 01    | 02A    | CSDiscardSlave                                        | Not done    |
| 01    | 02B    | CSChangeSlaveTarget                                   | Not done    |
| 01    | 02C    | CSChangeSlaveName                                     | Not done    |
| 01    | 02D    | CSRepairSlaveItems                                    | Not done    |
| 01    | 02E    | CSTurretState                                         | Not done    |
| 01    | 02F    | CSDestroyItem                                         | Not done    |
| 01    | 030    | CSSplitBagItem                                        | Not done    |
| 01    | 031    | CSSwapItems                                           | Not done    |
| 01    | 032    | -                                                     | -           |
| 01    | 033    | CSRepairSingleEquipment                               | Not done    |
| 01    | 034    | CSRepairAllEquipments                                 | Not done    |
| 01    | 035    | -                                                     | -           |
| 01    | 036    | CSSplitCofferItem                                     | Not done    |
| 01    | 037    | CSSwapCofferItems                                     | Not done    |
| 01    | 038    | CSExpandSlots                                         | Not done    |
| 01    | 039    | CSSellBackpackGoods                                   | Not done    |
| 01    | 03A    | CSSpecialtyRatio                                      | Not done    |
| 01    | 03B    | CSListSpecialtyGoods                                  | Not done    |
| 01    | 03C    | CSBuySpecialtyItem                                    | Not done    |
| 01    | 03D    | CSDepositMoney                                        | Not done    |
| 01    | 03E    | CSWithdrawMoney                                       | Not done    |
| 01    | 03F    | CSResurrectCharacter                                  | Not done    |
| 01    | 040    | CSSetForceAttack                                      | Not done    |
| 01    | 041    | CSChallengeDuel                                       | Not done    |
| 01    | 042    | CSStartDuel                                           | Not done    |
| 01    | 043    | CSStartSkill                                          | Not done    |
| 01    | 044    | -                                                     | -           |
| 01    | 045    | CSStopCasting                                         | Not done    |
| 01    | 046    | CSRemoveBuff                                          | Not done    |
| 01    | 047    | CSConstructHouseTax                                   | Not done    |
| 01    | 048    | CSCreateHouse                                         | Not done    |
| 01    | 049    | CSDecorateHouse                                       | Not done    |
| 01    | 04A    | CSChangeHouseName                                     | Not done    |
| 01    | 04B    | CSChangeHousePermission                               | Not done    |
| 01    | 04C    | CSChangeHousePay                                      | Not done    |
| 01    | 04D    | CSRequestHouseTax                                     | Not done    |
| 01    | 04E    | CSAllowHousingRecover                                 | Not done    |
| 01    | 04F    | CSJoinUserChatChannel                                 | Not done    |
| 01    | 050    | CSLeaveChatChannel                                    | Not done    |
| 01    | 051    | CSSendChatMessage                                     | Not done    |
| 01    | 052    | CSConsoleCmdUsed                                      | Not done    |
| 01    | 053    | CSInteractNPC                                         | Not done    |
| 01    | 054    | CSInteractNPCEnd                                      | Not done    |
| 01    | 055    | CSBoardingTransfer                                    | Not done    |
| 01    | 056    | CSStartInteraction                                    | Not done    |
| 01    | 057    | -                                                     | -           |
| 01    | 058    | -                                                     | -           |
| 01    | 059    | CSSelectInteractionEx                                 | Not done    |
| 01    | 05A    | CSCofferInteraction                                   | Not done    |
| 01    | 05B    | -                                                     | -           |
| 01    | 05C    | CSCriminalLocked                                      | Not done    |
| 01    | 05D    | CSReplyImprisonOrTrial                                | Not done    |
| 01    | 05E    | CSReplyInviteJury                                     | Not done    |
| 01    | 05F    | CSJurySummoned                                        | Not done    |
| 01    | 060    | CSJuryEndTestimony                                    | Not done    |
| 01    | 061    | CSCancelTrial                                         | Not done    |
| 01    | 062    | CSJuryVerdict                                         | Not done    |
| 01    | 063    | CSReportCrime                                         | Not done    |
| 01    | 064    | CSJoinTrialAudience                                   | Not done    |
| 01    | 065    | CSLeaveTrialAudience                                  | Not done    |
| 01    | 066    | CSRequestJuryWaitingNumber                            | Not done    |
| 01    | 067    | CSInviteToTeam                                        | Not done    |
| 01    | 068    | CSInviteAreaToTeam                                    | Not done    |
| 01    | 069    | CSReplyToJoinTeam                                     | Not done    |
| 01    | 06A    | CSLeaveTeam                                           | Not done    |
| 01    | 06B    | CSKickTeamMember                                      | Not done    |
| 01    | 06C    | CSMakeTeamOwner                                       | Not done    |
| 01    | 06D    | CSSetTeamOfficer                                      | Not done    |
| 01    | 06E    | CSConvertToRaidTeam                                   | Not done    |
| 01    | 06F    | CSMoveTeamMember                                      | Not done    |
| 01    | 070    | -                                                     | -           |
| 01    | 071    | CSChangeLootingRule                                   | Not done    |
| 01    | 072    | CSDismissTeam                                         | Not done    |
| 01    | 073    | CSSetTeamMemberRole                                   | Not done    |
| 01    | 074    | CSSetOverHeadMarker                                   | Not done    |
| 01    | 075    | CSSetPingPos                                          | Not done    |
| 01    | 076    | CSMoveUnit                                            | Not done    |
| 01    | 077    | CSSkillControllerState                                | Not done    |
| 01    | 078    | CSCreateSkillController                               | Not done    |
| 01    | 079    | CSActiveWeaponChanged                                 | Not done    |
| 01    | 07A    | CSSetCraftingPay                                      | Not done    |
| 01    | 07B    | CSChangeItemLook                                      | Not done    |
| 01    | 07C    | CSLootOpenBag                                         | Not done    |
| 01    | 07D    | CSLootItem                                            | Not done    |
| 01    | 07E    | CSLootCloseBag                                        | Not done    |
| 01    | 07F    | CSLootDice                                            | Not done    |
| 01    | 080    | CSLearnSkill                                          | Not done    |
| 01    | 081    | CSLearnBuff                                           | Not done    |
| 01    | 082    | CSResetSkills                                         | Not done    |
| 01    | 083    | -                                                     | -           |
| 01    | 084    | CSSwapAbility                                         | Not done    |
| 01    | 085    | -                                                     | -           |
| 01    | 086    | CSSendMail                                            | Not done    |
| 01    | 087    | CSListMail                                            | Not done    |
| 01    | 088    | CSListMailContinue                                    | Not done    |
| 01    | 089    | CSReadMail                                            | Not done    |
| 01    | 08A    | CSTakeAttachmentItem                                  | Not done    |
| 01    | 08B    | CSTakeAttachmentMoney                                 | Not done    |
| 01    | 08C    | CSPayChargeMoney                                      | Not done    |
| 01    | 08D    | CSDeleteMail                                          | Not done    |
| 01    | 08E    | CSReportSpam                                          | Not done    |
| 01    | 08F    | CSReturnMail                                          | Not done    |
| 01    | 090    | CSHireEmployee                                        | Not done    |
| 01    | 091    | CSFireEmployee                                        | Not done    |
| 01    | 092    | CSRemoveMate                                          | Not done    |
| 01    | 093    | CSRecallEmployee                                      | Not done    |
| 01    | 094    | CSChangeMateTarget                                    | Not done    |
| 01    | 095    | CSChangeMateName                                      | Not done    |
| 01    | 096    | CSMountMate                                           | Not done    |
| 01    | 097    | CSUnMountMate                                         | Not done    |
| 01    | 098    | CSChangeMateEquipment                                 | Not done    |
| 01    | 099    | CSChangeMateUserState                                 | Not done    |
| 01    | 09A    | CSExpressEmotion                                      | Not done    |
| 01    | 09B    | CSBuyItems                                            | Not done    |
| 01    | 09C    | CSBuyCoinItem                                         | Not done    |
| 01    | 09D    | CSSellItems                                           | Not done    |
| 01    | 09E    | CSListSoldItem                                        | Not done    |
| 01    | 09F    | CSBuyPriestBuff                                       | Not done    |
| 01    | 0A0    | CSUseTeleport                                         | Not done    |
| 01    | 0A1    | CSTeleportEnded                                       | Not done    |
| 01    | 0A2    | CSRepairPetItems                                      | Not done    |
| 01    | 0A3    | CSUpdateActionSlot                                    | Not done    |
| 01    | 0A4    | CSAuctionPost                                         | Not done    |
| 01    | 0A5    | CSAuctionSearch                                       | Not done    |
| 01    | 0A6    | CSBidAuction                                          | Not done    |
| 01    | 0A7    | CSCancelAuction                                       | Not done    |
| 01    | 0A8    | CSAuctionMyBidList                                    | Not done    |
| 01    | 0A9    | CSRollDice                                            | Not done    |
| 01    | 0AA    | -                                                     | -           |
| 01    | 0AB    | CSRequestNpcSpawnerList                               | Not done    |
| 01    | 0AC    | CSUpdateNpcSpawner                                    | Not done    |
| 01    | 0AD    | CSRemoveNpcSpawner                                    | Not done    |
| 01    | 0AE    | CSRemoveAllDoodadFromCell                             | Not done    |
| 01    | 0AF    | CSAddDoodadToCell                                     | Not done    |
| 01    | 0B0    | CSAddDoodadToCellEnded                                | Not done    |
| 01    | 0B1    | CSSetDoodadTimeAccel                                  | Not done    |
| 01    | 0B2    | -                                                     | -           |
| 01    | 0B3    | -                                                     | -           |
| 01    | 0B4    | CSRemoveAllFieldSlaves                                | Not done    |
| 01    | 0B5    | CSAddFieldSlave                                       | Not done    |
| 01    | 0B6    | <span style='color: red'>CSGmCommand</span>           | Not done    |
| 01    | 0B7    | CSHang                                                | Not done    |
| 01    | 0B8    | CSUnhang                                              | Not done    |
| 01    | 0B9    | CSUnbondDoodad                                        | Not done    |
| 01    | 0BA    | CSCompletedCinema                                     | Not done    |
| 01    | 0BB    | CSStartedCinema                                       | Not done    |
| 01    | 0BC    | CSRequestPermissionToPlayCinemaForDirectingMode       | Not done    |
| 01    | 0BD    | <span style='color: red'>CSEditorRemoveGimmick</span> | Not done    |
| 01    | 0BE    | <span style='color: red'>CSEditorAddGimmick</span>    | Not done    |
| 01    | 0BF    | CSInteractGimmick                                     | Not done    |
| 01    | 0C0    | CSWorldRayCasting                                     | Not done    |
| 01    | 0C1    | CSStartQuestContext                                   | Not done    |
| 01    | 0C2    | CSCompleteQuestContext                                | Not done    |
| 01    | 0C3    | CSDropQuestContext                                    | Not done    |
| 01    | 0C4    | CSResetQuestContext                                   | Not done    |
| 01    | 0C5    | CSAcceptCheatQuestContext                             | Not done    |
| 01    | 0C6    | CSQuestTalkMade                                       | Not done    |
| 01    | 0C7    | CSQuestStartWith                                      | Not done    |
| 01    | 0C8    | -                                                     | -           |
| 01    | 0C9    | CSTryQuestCompleteAsLetItDone                         | Not done    |
| 01    | 0CA    | CSUsePortal                                           | Not done    |
| 01    | 0CB    | CSDeletePortal                                        | Not done    |
| 01    | 0CC    | CSInstanceLoaded                                      | Not done    |
| 01    | 0CD    | CSApplyToInstantGame                                  | Not done    |
| 01    | 0CE    | CSCancelInstantGame                                   | Not done    |
| 01    | 0CF    | CSJoinInstantGame                                     | Not done    |
| 01    | 0D0    | CSEnteredInstantGameWorld                             | Not done    |
| 01    | 0D1    | CSCreateDoodad                                        | Not done    |
| 01    | 0D2    | CSSaveDoodadUccString                                 | Not done    |
| 01    | 0D3    | CSNaviTeleport                                        | Not done    |
| 01    | 0D4    | CSNaviOpenPortal                                      | Not done    |
| 01    | 0D5    | CSChangeDoodadPhase                                   | Not done    |
| 01    | 0D6    | CSNaviOpenBounty                                      | Not done    |
| 01    | 0D7    | CSStartTrade                                          | Not done    |
| 01    | 0D8    | CSCanStartTrade                                       | Not done    |
| 01    | 0D9    | CSCannotStartTrade                                    | Not done    |
| 01    | 0DA    | CSCancelTrade                                         | Not done    |
| 01    | 0DB    | CSPutupTradeItem                                      | Not done    |
| 01    | 0DC    | CSPutupTradeMoney                                     | Not done    |
| 01    | 0DD    | CSTakedownTradeItem                                   | Not done    |
| 01    | 0DE    | CSTradeLock                                           | Not done    |
| 01    | 0DF    | CSTradeOk                                             | Not done    |
| 01    | 0E0    | CSSaveTutorial                                        | Not done    |
| 01    | 0E1    | CSSetLogicDoodad                                      | Not done    |
| 01    | 0E2    | CSCleanupLogicLink                                    | Not done    |
| 01    | 0E3    | CSExecuteCraft                                        | Not done    |
| 01    | 0E4    | CSChangeAppellation                                   | Not done    |
| 01    | 0E5    | -                                                     | -           |
| 01    | 0E6    | -                                                     | -           |
| 01    | 0E7    | CSCreateShipyard                                      | Not done    |
| 01    | 0E8    | CSRestartMainQuest                                    | Not done    |
| 01    | 0E9    | CSSetLpManageCharacter                                | Not done    |
| 01    | 0EA    | CSUpgradeExpertLimit                                  | Not done    |
| 01    | 0EB    | CSDowngradeExpertLimit                                | Not done    |
| 01    | 0EC    | -                                                     | -           |
| 01    | 0ED    | CSSearchList                                          | Not done    |
| 01    | 0EE    | CSAddFriend                                           | Not done    |
| 01    | 0EF    | CSDeleteFriend                                        | Not done    |
| 01    | 0F0    | CSCharDetail                                          | Not done    |
| 01    | 0F1    | CSAddBlockedUser                                      | Not done    |
| 01    | 0F2    | CSDeleteBlockedUser                                   | Not done    |
| 01    | 0F3    | CSShowQuestArea                                       | Not done    |
| 01    | 0F4    | CSShowCommonFarmArea                                  | Not done    |
| 01    | 0F5    | CSRequestCommonFarmList                               | Not done    |
| 01    | 0F6    | CSCheckDemoMode                                       | Not done    |
| 01    | 0F7    | CSResetDemoChar                                       | Not done    |
| 01    | 0F8    | CSRemoveAreaSpheres                                   | Not done    |
| 01    | 0F9    | CSPlaceAreaSpheres                                    | Not done    |
| 01    | 0FA    | CSRemoveCommonFarms                                   | Not done    |
| 01    | 0FB    | CSPlaceCommonFarm                                     | Not done    |
| 01    | 0FC    | CSNotifySubZone                                       | Not done    |
| 01    | 0FD    | -                                                     | -           |
| 01    | 0FE    | CSItemUcc                                             | Not done    |
| 01    | 0FF    | CSRequestUIData                                       | Not done    |
| 01    | 100    | CSSaveUIData                                          | Not done    |
| 01    | 101    | CSBroadcastVisualOption                               | Done        |
| 01    | 102    | CSRestrictCheck                                       | Not done    |
| 01    | 103    | CSICSMenuList                                         | Not done    |
| 01    | 104    | CSICSGoodsList                                        | Not done    |
| 01    | 105    | CSICSBuyGood                                          | Not done    |
| 01    | 106    | CSCharacterFindByName                                 | Not done    |
| 01    | 107    | CSRequestSetBountyMoney                               | Not done    |
| 01    | 108    | CSUpdateBounty                                        | Not done    |
| 01    | 109    | CSSendUserMusic                                       | Not done    |
| 01    | 125    | CSQuestAcceptConditional                              | Not done    |

### ServerClient

| Level | Opcode | Name                                                    | Status      |
|-------|--------|---------------------------------------------------------|-------------|
| 01    | 000    | SCX2WorldToClient                                       | ?           |
| 01    | 001    | SCReconnectAuth                                         | Done        |
| 01    | 002    | SCPrepareLeaveWorld                                     | Not done    |
| 01    | 003    | SCLeaveWorldGranted                                     | Not done    |
| 01    | 004    | SCLeaveWorldCanceled                                    | Not done    |
| 01    | 005    | SCInitialConfig                                         | Done        |
| 01    | 006    | SCFactionList                                           | Not done    |
| 01    | 007    | SCFactionRelationList                                   | Not done    |
| 01    | 008    | -                                                       | -           |
| 01    | 009    | SCExpeditionRolePolicyList                              | Not done    |
| 01    | 00A    | SCExpeditionRolePolicyChanged                           | Not done    |
| 01    | 00B    | SCExpeditionRoleChanged                                 | Not done    |
| 01    | 00C    | SCExpeditionOwnerChanged                                | Not done    |
| 01    | 00D    | SCExpeditionNameChanged                                 | Not done    |
| 01    | 00E    | SCFactionCreated                                        | Not done    |
| 01    | 00F    | SCExpeditionSponsorChanged                              | Not done    |
| 01    | 010    | SCExpeditionDismissed                                   | Not done    |
| 01    | 011    | SCExpeditionMemberList                                  | Not done    |
| 01    | 012    | SCExpeditionMemberStatusChanged                         | Not done    |
| 01    | 013    | SCFactionSetRelationState                               | Not done    |
| 01    | 014    | SCExpeditionInvitation                                  | Not done    |
| 01    | 015    | SCUnitFactionChanged                                    | Not done    |
| 01    | 016    | SCUnitExpeditionChanged                                 | Not done    |
| 01    | 017    | SCDominionData                                          | Not done    |
| 01    | 018    | SCDominionDeleted                                       | Not done    |
| 01    | 019    | SCDominionOwnerChangedByGm                              | Not done    |
| 01    | 01A    | SCDominionTaxRate                                       | Not done    |
| 01    | 01B    | SCDominionTaxMoney                                      | Not done    |
| 01    | 01C    | SCFactionIndependence                                   | Not done    |
| 01    | 01D    | SCFamilyInvitation                                      | Not done    |
| 01    | 01E    | SCFamilyCreated                                         | Not done    |
| 01    | 01F    | SCFamilyRemoved                                         | Not done    |
| 01    | 020    | SCFamilyDesc                                            | Not done    |
| 01    | 021    | SCFamilyMemberAdded                                     | Not done    |
| 01    | 022    | SCFamilyMemberRemoved                                   | Not done    |
| 01    | 023    | SCFamilyOwnerChanged                                    | Not done    |
| 01    | 024    | SCFamilyTitleChanged                                    | Not done    |
| 01    | 025    | SCFamilyTitle                                           | Not done    |
| 01    | 026    | SCFamilyMemberOnline                                    | Not done    |
| 01    | 027    | SCCreateCharacterResponse                               | In progress |
| 01    | 028    | SCDeleteCharacterResponse                               | Not done    |
| 01    | 029    | SCEditCharacterResponse                                 | Not done    |
| 01    | 02A    | SCCharacterDeleted                                      | Not done    |
| 01    | 02B    | SCCancelCharacterDeleteResponse                         | Not done    |
| 01    | 02C    | SCCharacterCreationFailed                               | Done        |
| 01    | 02D    | SCCharacterList                                         | Done        |
| 01    | 02E    | SCRaceCongestion                                        | Not done    |
| 01    | 02F    | SCCharacterState                                        | Not done    |
| 01    | 030    | SCCharacterMates                                        | Not done    |
| 01    | 031    | SCNotifyResurrection                                    | Not done    |
| 01    | 032    | SCCharacterResurrected                                  | Not done    |
| 01    | 033    | SCForceAttackSet                                        | Not done    |
| 01    | 034    | -                                                       | -           |
| 01    | 035    | SCCharacterLaborPowerChanged                            | Not done    |
| 01    | 036    | SCAddActionPoint                                        | Not done    |
| 01    | 037    | SCLpManaged                                             | Not done    |
| 01    | 038    | SCCharacterBound                                        | Not done    |
| 01    | 039    | SCCooldowns                                             | Not done    |
| 01    | 03A    | SCCharacterInvenInit                                    | Not done    |
| 01    | 03B    | SCCharacterInvenContents                                | Not done    |
| 01    | 03C    | SCInvenExpanded                                         | Not done    |
| 01    | 03D    | SCFriends                                               | Not done    |
| 01    | 03E    | SCSearchList                                            | Not done    |
| 01    | 03F    | SCAddFriend                                             | Not done    |
| 01    | 040    | SCDeleteFriend                                          | Not done    |
| 01    | 041    | SCFriendStatusChanged                                   | Not done    |
| 01    | 042    | SCCharDetail                                            | Not done    |
| 01    | 043    | SCBlockedUsers                                          | Not done    |
| 01    | 044    | SCAddBlockedUser                                        | Not done    |
| 01    | 045    | SCDeleteBlockedUser                                     | Not done    |
| 01    | 046    | SCLoginCharInfoHouse                                    | Not done    |
| 01    | 047    | SCCharBrief                                             | Not done    |
| 01    | 048    | SCPlaytime                                              | Not done    |
| 01    | 049    | SCCharacterPortals                                      | Not done    |
| 01    | 04A    | SCCharacterReturnDistricts                              | Not done    |
| 01    | 04B    | SCPortalInfoSaved                                       | Not done    |
| 01    | 04C    | SCPortalDeleted                                         | Not done    |
| 01    | 04D    | -                                                       | -           |
| 01    | 04E    | SCUnitPortalUsed                                        | Not done    |
| 01    | 04F    | SCSlaveCreated                                          | Not done    |
| 01    | 050    | SCSlaveRemoved                                          | Not done    |
| 01    | 051    | SCSlaveDespawn                                          | Not done    |
| 01    | 052    | SCSlaveBound                                            | Not done    |
| 01    | 053    | -                                                       | -           |
| 01    | 054    | SCMySlave                                               | Not done    |
| 01    | 055    | SCUnitState                                             | Not done    |
| 01    | 056    | SCUnitsRemoved                                          | Not done    |
| 01    | 057    | SCUnitMovements                                         | Not done    |
| 01    | 058    | SCOneUnitMovement                                       | Not done    |
| 01    | 059    | SCSkillControllerState                                  | Not done    |
| 01    | 05A    | SCUnitOffline                                           | Not done    |
| 01    | 05B    | SCActiveWeaponChanged                                   | Not done    |
| 01    | 05C    | SCUnitNameChanged                                       | Not done    |
| 01    | 05D    | SCUnitDeath                                             | Not done    |
| 01    | 05E    | SCTeleportUnit                                          | Not done    |
| 01    | 05F    | SCBlinkUnit                                             | Not done    |
| 01    | 060    | SCKnockBackUnit                                         | Not done    |
| 01    | 061    | -                                                       | -           |
| 01    | 062    | SCUnitAttached                                          | Not done    |
| 01    | 063    | SCUnitDetached                                          | Not done    |
| 01    | 064    | SCUnitInvisible                                         | Not done    |
| 01    | 065    | SCNpcInteractionSkillList                               | Not done    |
| 01    | 066    | SCNpcInteractionEndedByZone                             | Not done    |
| 01    | 067    | -                                                       | -           |
| 01    | 068    | SCWorldInteractionSkillList                             | Not done    |
| 01    | 069    | SCWorldInteractionCanceled                              | Not done    |
| 01    | 06A    | SCNpcInteractionStatusChanged                           | Not done    |
| 01    | 06B    | SCVegetationCutdowning                                  | Not done    |
| 01    | 06C    | SCNpcFriendshipList                                     | Not done    |
| 01    | 06D    | SCNpcFriendshipChanged                                  | Not done    |
| 01    | 06E    | SCTargetChanged                                         | Not done    |
| 01    | 06F    | SCCombatEngaged                                         | Not done    |
| 01    | 070    | SCCombatCleared                                         | Not done    |
| 01    | 071    | SCCVFCombatRelationship                                 | Not done    |
| 01    | 072    | SCFVFCombatRelationship                                 | Not done    |
| 01    | 073    | SCCombatFirstHit                                        | Not done    |
| 01    | 074    | <span style='color: red'>SCDumpCombatStat</span>        | Not done    |
| 01    | 075    | SCDuelChallenged                                        | Not done    |
| 01    | 076    | SCDuelStartCountdown                                    | Not done    |
| 01    | 077    | SCDuelStarted                                           | Not done    |
| 01    | 078    | SCDuelEnded                                             | Not done    |
| 01    | 079    | SCDuelState                                             | Not done    |
| 01    | 07A    | SCItemTaskSuccess                                       | Not done    |
| 01    | 07B    | -                                                       | -           |
| 01    | 07C    | SCItemDetailUpdated                                     | Not done    |
| 01    | 07D    | SCUnitEquipmentsChanged                                 | Not done    |
| 01    | 07E    | SCUnitEquipmentIds                                      | Not done    |
| 01    | 07F    | SCCofferContentsUpdate                                  | Not done    |
| 01    | 080    | SCItemAcquisition                                       | Not done    |
| 01    | 081    | SCSyncItemLifespan                                      | Not done    |
| 01    | 082    | SCSpecialtyRatio                                        | Not done    |
| 01    | 083    | SCSpecialtyGoods                                        | Not done    |
| 01    | 084    | SCSkillStarted                                          | Not done    |
| 01    | 085    | SCSkillFired                                            | Not done    |
| 01    | 086    | SCSkillEnded                                            | Not done    |
| 01    | 087    | SCSkillStopped                                          | Not done    |
| 01    | 088    | SCCastingStopped                                        | Not done    |
| 01    | 089    | SCCastingDelayed                                        | Not done    |
| 01    | 08A    | SCUnitDamaged                                           | Not done    |
| 01    | 08B    | SCUnitHealed                                            | Not done    |
| 01    | 08C    | SCUnitTracked                                           | Not done    |
| 01    | 08D    | SCBombUpdated                                           | Not done    |
| 01    | 08E    | SCCombatText                                            | Not done    |
| 01    | 08F    | -                                                       | -           |
| 01    | 090    | SCSkillCooldownReset                                    | Not done    |
| 01    | 091    | SCPlotEvent                                             | Not done    |
| 01    | 092    | SCPlotEnded                                             | Not done    |
| 01    | 093    | SCPlotProcessingTime                                    | Not done    |
| 01    | 094    | SCPlotCastingStopped                                    | Not done    |
| 01    | 095    | SCPlotChannelingStopped                                 | Not done    |
| 01    | 096    | SCEnvDamage                                             | Not done    |
| 01    | 097    | SCBuffState                                             | Not done    |
| 01    | 098    | SCBuffCreated                                           | Not done    |
| 01    | 099    | SCBuffRemoved                                           | Not done    |
| 01    | 09A    | -                                                       | -           |
| 01    | 09B    | SCBuffUpdated                                           | Not done    |
| 01    | 09C    | SCUnitPoints                                            | Not done    |
| 01    | 09D    | SCUnitBountyMoney                                       | Not done    |
| 01    | 09E    | SCHouseState                                            | Not done    |
| 01    | 09F    | SCHouseBuildProgress                                    | Not done    |
| 01    | 0A0    | SCHousePermissionChanged                                | Not done    |
| 01    | 0A1    | SCHouseBuildPayChanged                                  | Not done    |
| 01    | 0A2    | SCHouseDemolished                                       | Not done    |
| 01    | 0A3    | SCMyHouse                                               | Not done    |
| 01    | 0A4    | SCMyHouseRemoved                                        | Not done    |
| 01    | 0A5    | SCHouseFarm                                             | Not done    |
| 01    | 0A6    | SCHouseTaxInfo                                          | Not done    |
| 01    | 0A7    | SCConstructHouseTax                                     | Not done    |
| 01    | 0A8    | SCHousingRecoverToggle                                  | Not done    |
| 01    | 0A9    | SCJoinedChatChannel                                     | Not done    |
| 01    | 0AA    | SCLeavedChatChannel                                     | Not done    |
| 01    | 0AB    | SCChatMessage                                           | Not done    |
| 01    | 0AC    | SCNpcChatMessage                                        | Not done    |
| 01    | 0AD    | SCNoticeMessage                                         | Not done    |
| 01    | 0AE    | SCChatFailed                                            | Not done    |
| 01    | 0AF    | SCChatLocalizedMessage                                  | Not done    |
| 01    | 0B0    | SCChatSpamDelay                                         | Done        |
| 01    | 0B1    | SCAskToJoinTeam                                         | Not done    |
| 01    | 0B2    | SCAskToJoinTeamArea                                     | Not done    |
| 01    | 0B3    | SCJoinedTeam                                            | Not done    |
| 01    | 0B4    | SCRejectedTeam                                          | Not done    |
| 01    | 0B5    | SCLeavedTeam                                            | Not done    |
| 01    | 0B6    | SCTeamDismissed                                         | Not done    |
| 01    | 0B7    | SCTeamMemberJoined                                      | Not done    |
| 01    | 0B8    | SCTeamMemberLeaved                                      | Not done    |
| 01    | 0B9    | SCTeamMemberDisconnected                                | Not done    |
| 01    | 0BA    | SCTeamOwnerChanged                                      | Not done    |
| 01    | 0BB    | SCTeamOfficerChanged                                    | Not done    |
| 01    | 0BC    | SCTeamMemberRoleChanged                                 | Not done    |
| 01    | 0BD    | SCTeamBecameRaidTeam                                    | Not done    |
| 01    | 0BE    | SCTeamMemberMoved                                       | Not done    |
| 01    | 0BF    | -                                                       | -           |
| 01    | 0C0    | SCTeamLootingRuleChanged                                | Not done    |
| 01    | 0C1    | SCRefreshTeamMember                                     | Not done    |
| 01    | 0C2    | SCTeamRemoteMembersEx                                   | Not done    |
| 01    | 0C3    | SCTeamAreaInvited                                       | Not done    |
| 01    | 0C4    | SCOverHeadMarkerSet                                     | Not done    |
| 01    | 0C5    | SCTeamPingPos                                           | Not done    |
| 01    | 0C6    | SCSiegeState                                            | Not done    |
| 01    | 0C7    | SCSiegeDeclared                                         | Not done    |
| 01    | 0C8    | SCSiegeReinforce                                        | Not done    |
| 01    | 0C9    | SCSiegeMember                                           | Not done    |
| 01    | 0CA    | SCSiegeAlert                                            | Not done    |
| 01    | 0CB    | SCConflictZoneState                                     | Not done    |
| 01    | 0CC    | SCConflictZoneHonorPointSum                             | Not done    |
| 01    | 0CD    | SCTimeOfDay                                             | Not done    |
| 01    | 0CE    | SCDetailedTimeOfDay                                     | Not done    |
| 01    | 0CF    | SCQuests                                                | Not done    |
| 01    | 0D1    | -                                                       | -           |
| 01    | 0D2    | SCCraftItemUnlock                                       | Not done    |
| 01    | 0D3    | SCItemLookChanged                                       | Not done    |
| 01    | 0D4    | SCLootableState                                         | Not done    |
| 01    | 0D5    | SCUnitLootingState                                      | Not done    |
| 01    | 0D6    | SCLootBagData                                           | Not done    |
| 01    | 0D7    | SCLootItemTook                                          | Not done    |
| 01    | 0D8    | SCLootItemFailed                                        | Not done    |
| 01    | 0D9    | SCLootDice                                              | Not done    |
| 01    | 0D0    | SCCompletedQuests                                       | Not done    |
| 01    | 0DA    | SCLootDiceNotify                                        | Not done    |
| 01    | 0DB    | SCLootDiceSummary                                       | Not done    |
| 01    | 0DC    | SCExpChanged                                            | Not done    |
| 01    | 0DD    | SCRecoverableExp                                        | Not done    |
| 01    | 0DE    | SCMileageChanged                                        | Not done    |
| 01    | 0DF    | SCLevelChanged                                          | Not done    |
| 01    | 0E0    | SCUnitModelPostureChanged                               | Not done    |
| 01    | 0E1    | -                                                       | -           |
| 01    | 0E2    | SCSkillLearned                                          | Not done    |
| 01    | 0E3    | SCSkillUpgraded                                         | Not done    |
| 01    | 0E4    | SCBuffLearned                                           | Not done    |
| 01    | 0E5    | SCSkillsReset                                           | Not done    |
| 01    | 0E6    | SCAbilitySwapped                                        | Not done    |
| 01    | 0E7    | SCErrorMsg                                              | Not done    |
| 01    | 0E8    | -                                                       | -           |
| 01    | 0E9    | SCDoodadCreated                                         | Not done    |
| 01    | 0EA    | SCDoodadRemoved                                         | Not done    |
| 01    | 0EB    | SCDoodadChanged                                         | Not done    |
| 01    | 0EC    | SCDoodadPhaseChanged                                    | Not done    |
| 01    | 0ED    | SCDoodadPuzzleScene                                     | Not done    |
| 01    | 0EE    | SCDoodadQuestAccept                                     | Not done    |
| 01    | 0EF    | SCDoodadsCreated                                        | Not done    |
| 01    | 0F0    | SCDoodadsRemoved                                        | Not done    |
| 01    | 0F1    | SCDoodadOriginator                                      | Not done    |
| 01    | 0F2    | SCMailFailed                                            | Not done    |
| 01    | 0F3    | SCCountUnreadMail                                       | Not done    |
| 01    | 0F4    | SCMailSent                                              | Not done    |
| 01    | 0F5    | SCGotMail                                               | Not done    |
| 01    | 0F6    | SCMailList                                              | Not done    |
| 01    | 0F7    | SCMailListEnd                                           | Not done    |
| 01    | 0F8    | SCMailBody                                              | Not done    |
| 01    | 0F9    | SCMailReceiverOpened                                    | Not done    |
| 01    | 0FA    | SCAttachmentTaken                                       | Not done    |
| 01    | 0FB    | SCChargeMoneyPaid                                       | Not done    |
| 01    | 0FC    | SCMailDeleted                                           | Not done    |
| 01    | 0FD    | SCSpamReported                                          | Not done    |
| 01    | 0FE    | SCMailReturned                                          | Not done    |
| 01    | 0FF    | SCMailStatusUpdated                                     | Not done    |
| 01    | 100    | SCMailRemoved                                           | Not done    |
| 01    | 101    | SCMineAmount                                            | Not done    |
| 01    | 102    | SCNewMate                                               | Not done    |
| 01    | 103    | SCMateSpawned                                           | Not done    |
| 01    | 104    | SCMateFired                                             | Not done    |
| 01    | 105    | SCMateEquipmentChanged                                  | Not done    |
| 01    | 106    | -                                                       | -           |
| 01    | 107    | -                                                       | -           |
| 01    | 108    | SCEmotionExpressed                                      | Not done    |
| 01    | 109    | SCSoldItemList                                          | Not done    |
| 01    | 10A    | SCActionSlots                                           | Not done    |
| 01    | 10B    | SCAuctionPosted                                         | Not done    |
| 01    | 10C    | SCAuctionSearched                                       | Not done    |
| 01    | 10D    | SCAuctionBid                                            | Not done    |
| 01    | 10E    | SCAuctionCanceled                                       | Not done    |
| 01    | 10F    | SCAuctionMessage                                        | Not done    |
| 01    | 110    | SCDiceValue                                             | Not done    |
| 01    | 111    | SCNpcSpawner                                            | Not done    |
| 01    | 112    | SCActability                                            | Not done    |
| 01    | 113    | SCHung                                                  | Not done    |
| 01    | 114    | SCUnhung                                                | Not done    |
| 01    | 115    | SCBondDoodad                                            | Not done    |
| 01    | 116    | SCUnbondDoodad                                          | Not done    |
| 01    | 117    | SCPlaySequence                                          | Not done    |
| 01    | 118    | SCGimmicksCreated                                       | Not done    |
| 01    | 119    | SCGimmicksRemoved                                       | Not done    |
| 01    | 11A    | SCGimmickMovement                                       | Not done    |
| 01    | 11B    | SCGimmickJointsBroken                                   | Not done    |
| 01    | 11C    | SCGimmickResetJoints                                    | Not done    |
| 01    | 11D    | SCGimmickGrasped                                        | Not done    |
| 01    | 11E    | SCWorldRayCastingResult                                 | Not done    |
| 01    | 11F    | SCWorldAimPoint                                         | Not done    |
| 01    | 120    | SCWorldAABB                                             | Not done    |
| 01    | 121    | SCQuestContextFailed                                    | Not done    |
| 01    | 122    | SCQuestContextStarted                                   | Not done    |
| 01    | 123    | SCQuestUnitReqFailed                                    | Not done    |
| 01    | 124    | SCQuestContextUpdated                                   | Not done    |
| 01    | 125    | -                                                       | -           |
| 01    | 126    | SCQuestContextCompleted                                 | Not done    |
| 01    | 127    | SCQuestContextReset                                     | Not done    |
| 01    | 128    | SCDoodadCompleteQuest                                   | Not done    |
| 01    | 129    | SCQuestRewardedByMail                                   | Not done    |
| 01    | 12A    | SCQuestList                                             | Not done    |
| 01    | 12B    | SCQuestMailSent                                         | Not done    |
| 01    | 12C    | SCSoundAreaEvent                                        | Not done    |
| 01    | 12D    | SCAreaChatBubble                                        | Not done    |
| 01    | 12E    | SCChatBubble                                            | Not done    |
| 01    | 12F    | SCAreaTeamMessage                                       | Not done    |
| 01    | 130    | SCDoodadSound                                           | Not done    |
| 01    | 131    | SCDoodadPhaseMsg                                        | Not done    |
| 01    | 132    | SCDoodadUccString                                       | Not done    |
| 01    | 133    | -                                                       | -           |
| 01    | 134    | SCDoodadUccData                                         | Not done    |
| 01    | 135    | SCUccCharacterNameLoaded                                | Not done    |
| 01    | 136    | SCNaviTeleport                                          | Not done    |
| 01    | 137    | SCItemUccDataChanged                                    | Not done    |
| 01    | 138    | SCCanStartTrade                                         | Not done    |
| 01    | 139    | SCCannotStartTrade                                      | Not done    |
| 01    | 13A    | SCTradeStarted                                          | Not done    |
| 01    | 13B    | SCTradeCanceled                                         | Not done    |
| 01    | 13C    | SCTradeItemPutup                                        | Not done    |
| 01    | 13D    | SCOtherTradeItemPutup                                   | Not done    |
| 01    | 13E    | SCTradeMoneyPutup                                       | Not done    |
| 01    | 13F    | SCOtherTradeMoneyPutup                                  | Not done    |
| 01    | 140    | SCTradeItemTookdown                                     | Not done    |
| 01    | 141    | SCOtherTradeItemTookdown                                | Not done    |
| 01    | 142    | SCTradeOkUpdate                                         | Not done    |
| 01    | 143    | SCTradeLockUpdate                                       | Not done    |
| 01    | 144    | SCTradeMade                                             | Not done    |
| 01    | 145    | SCTowerDefList                                          | Not done    |
| 01    | 146    | SCTowerDefStart                                         | Not done    |
| 01    | 147    | SCTowerDefEnd                                           | Not done    |
| 01    | 148    | SCTowerDefWaveStart                                     | Not done    |
| 01    | 149    | SCCrimeChanged                                          | Not done    |
| 01    | 14A    | SCCriminalArrested                                      | Not done    |
| 01    | 14B    | SCAskImprisonOrTrial                                    | Not done    |
| 01    | 14C    | SCInviteJury                                            | Not done    |
| 01    | 14D    | SCSummonJury                                            | Not done    |
| 01    | 14E    | SCJuryBeSeated                                          | Not done    |
| 01    | 14F    | SCSummonDefendant                                       | Not done    |
| 01    | 150    | SCCrimeData                                             | Not done    |
| 01    | 151    | SCCrimeRecords                                          | Not done    |
| 01    | 152    | SCChangeTrialState                                      | Not done    |
| 01    | 153    | SCChangeJuryOKCount                                     | Not done    |
| 01    | 154    | SCChangeJuryVerdictCount                                | Not done    |
| 01    | 155    | SCTrialWaitStatus                                       | Not done    |
| 01    | 156    | SCJuryWaitStatus                                        | Not done    |
| 01    | 157    | SCRulingStatus                                          | Not done    |
| 01    | 158    | SCRulingClosed                                          | Not done    |
| 01    | 159    | SCTrialAudienceJoined                                   | Not done    |
| 01    | 15A    | SCTrialAudienceLeft                                     | Not done    |
| 01    | 15B    | SCTrialInfo                                             | Not done    |
| 01    | 15C    | SCJuryWaitingNumber                                     | Not done    |
| 01    | 15D    | SCTrialCandled                                          | Not done    |
| 01    | 15E    | SCBotSuspectReported                                    | Not done    |
| 01    | 15F    | SCBotSuspectArrested                                    | Not done    |
| 01    | 160    | SCUnderWater                                            | Not done    |
| 01    | 161    | SCCharacterGamePoints                                   | Not done    |
| 01    | 162    | SCHonorPointAdded                                       | Not done    |
| 01    | 163    | SCLivingPointAdded                                      | Not done    |
| 01    | 164    | SCJuryPointChanged                                      | Not done    |
| 01    | 165    | SCAppliedToInstantGame                                  | Not done    |
| 01    | 166    | SCInstantGameState                                      | Not done    |
| 01    | 167    | SCInviteToInstantGame                                   | Not done    |
| 01    | 168    | SCInstantGameInviteTimeout                              | Not done    |
| 01    | 169    | SCInstantGameJoined                                     | Not done    |
| 01    | 16A    | SCInstantGameStart                                      | Not done    |
| 01    | 16B    | SCInstantGameEnd                                        | Not done    |
| 01    | 16C    | SCInstantGameAddPoint                                   | Not done    |
| 01    | 16D    | SCInstantGameKill                                       | Not done    |
| 01    | 16E    | SCInstantGameKillstreak                                 | Not done    |
| 01    | 16F    | SCLoadInstance                                          | Not done    |
| 01    | 170    | SCProcessingInstance                                    | Not done    |
| 01    | 171    | <span style='color: red'>SCGmCommand</span>             | Not done    |
| 01    | 172    | <span style='color: red'>SCGmDumpInventory</span>       | Not done    |
| 01    | 173    | <span style='color: red'>SCGmDumpEquipment</span>       | Not done    |
| 01    | 174    | <span style='color: red'>SCGmDumpQuests</span>          | Not done    |
| 01    | 175    | <span style='color: red'>SCGmDumpCompletedQuests</span> | Not done    |
| 01    | 176    | <span style='color: red'>SCGmDumpNpc</span>             | Not done    |
| 01    | 177    | SCKicked                                                | Not done    |
| 01    | 178    | SCAccountWarned                                         | Done        |
| 01    | 179    | SCAboxTeleport                                          | Not done    |
| 01    | 17A    | SCDelayedTaskOnInGameNotify                             | Not done    |
| 01    | 17B    | SCAppellations                                          | Not done    |
| 01    | 17C    | SCAppellationGained                                     | Not done    |
| 01    | 17D    | SCAppellationChanged                                    | Not done    |
| 01    | 17E    | SCTutorialSaved                                         | Not done    |
| 01    | 17F    | SCEmblemUploaded                                        | Not done    |
| 01    | 180    | SCEmblemDownloaded                                      | Not done    |
| 01    | 181    | SCShipyardState                                         | Not done    |
| 01    | 182    | SCSlaveState                                            | Not done    |
| 01    | 183    | SCMateState                                             | Not done    |
| 01    | 184    | <span style='color: red'>SCDumpCombatInfo</span>        | Not done    |
| 01    | 185    | SCUccCheckec                                            | Not done    |
| 01    | 186    | SCShowQuestArea                                         | Not done    |
| 01    | 187    | SCShowCommonFarm                                        | Not done    |
| 01    | 188    | SCResponseCommonFarmList                                | Not done    |
| 01    | 189    | SCTelescopeToggled                                      | Not done    |
| 01    | 18A    | SCTelescopeUnits                                        | Not done    |
| 01    | 18B    | SCTransferTelescopeToggled                              | Not done    |
| 01    | 18C    | SCTransferTelescopeUnits                                | Not done    |
| 01    | 18D    | SCSchoolOfFishFinderToggled                             | Not done    |
| 01    | 18E    | SCSchoolOfFishDoodads                                   | Not done    |
| 01    | 18F    | SCShowDemoModeUi                                        | Not done    |
| 01    | 190    | SCDemoCharResetItem                                     | Not done    |
| 01    | 191    | SCDemoCharResetLoc                                      | Not done    |
| 01    | 192    | SCDemoResetActionSlot                                   | Not done    |
| 01    | 193    | SCSetBreath                                             | Not done    |
| 01    | 194    | SCAggroTargetChanged                                    | Not done    |
| 01    | 195    | SCDoodadReqBattleField                                  | Not done    |
| 01    | 196    | SCCraftFailed                                           | Not done    |
| 01    | 197    | SCExpertLimitModified                                   | Not done    |
| 01    | 198    | SCAccountInfo                                           | Done        |
| 01    | 199    | <span style='color: red'>SCAiDebug</span>               | Not done    |
| 01    | 19A    | SCAIAggro                                               | Not done    |
| 01    | 19B    | SCHSRequest                                             | Not done    |
| 01    | 19C    | SCUnitLocation                                          | Not done    |
| 01    | 19D    | SCRestrictInfo                                          | Not done    |
| 01    | 19E    | SCIsUnitInFarm                                          | Not done    |
| 01    | 19F    | SCResponseUIData                                        | Not done    |
| 01    | 1A0    | SCUnitVisualOptions                                     | Not done    |
| 01    | 1A1    | SCNotifyUIMessage                                       | Not done    |
| 01    | 1A2    | SCRefreshInCharacterList                                | Done        |
| 01    | 1A3    | SCResultRestrictCheck                                   | Not done    |
| 01    | 1A4    | SCICSMenuList                                           | Not done    |
| 01    | 1A5    | SCICSGoodList                                           | Not done    |
| 01    | 1A6    | SCICSGoodDetail                                         | Not done    |
| 01    | 1A7    | SCICSCheckTime                                          | Not done    |
| 01    | 1A8    | SCICSBuyResult                                          | Not done    |
| 01    | 1A9    | SCICSSyncGood                                           | Not done    |
| 01    | 1AA    | SCCharacterFindByName                                   | Not done    |
| 01    | 1AB    | SCICSCashPoint                                          | Not done    |
| 01    | 1AC    | SCBountyList                                            | Not done    |
| 01    | 1AD    | SCSetBountyPermitted                                    | Not done    |
| 01    | 1AE    | SCSetBountyDone                                         | Not done    |
| 01    | 1AF    | SCBountyPaid                                            | Not done    |
| 01    | 1B0    | SCInvitationCanceled                                    | Not done    |
| 01    | 1B1    | SCPlaySound                                             | Not done    |
| 01    | 1B2    | SCSendUserMusic                                         | Not done    |

### ClientEditor

| Opcode | Name                   | Status   |
|--------|------------------------|----------|
| 000    | -                      | -        |
| 001    | CELogin                | Done     |
| 002    | CERequestCellLockInfo  | Not done |
| 003    | CERequestLock          | Not done |
| 004    | CERequestMultipleLocks | Not done |
| 005    | CEIncrease Version     | Not done |
| 006    | CERequestCellLock      | Not done |
| 007    | CEPong                 | Not done |
| 008    | CEChat                 | Not done |
| 009    | -                      | -        |
| 00A    | CEBatchRequest         | Not done |
| 00B    | -                      | -        |
| 00C    | CEBatchQuery           | Not done |
| 00D    | -                      | -        |
| 00E    | CECommand              | Not done |

### EditorClient

| Opcode | Name                      | Status   |
|--------|---------------------------|----------|
| 000    | -                         | -        |
| 001    | ECLoginResponse           | Done     |
| 002    | ECSubCellLockInfo         | Not done |
| 003    | ECCellLockInfo            | Not done |
| 004    | ECLockResponse            | Not done |
| 005    | ECMultipleLockResponse    | Not done |
| 006    | ECIncreaseVersionResponse | Not done |
| 007    | ECPing                    | Not done |
| 008    | ECChat                    | Not done |
| 009    | -                         | -        |
| 00A    | ECBatchQueryResult        | Not done |
| 00B    | -                         | -        |
| 00C    | ECBatchRequestResponse    | Not done |
| 00D    | -                         | -        |
| 00E    | -                         | -        |
| 00F    | ECNotice                  | Not done |
| 010    | ECTransactionReverted     | Not done |

### ZoneWorld

| Opcode | Name                           | Status   |
|--------|--------------------------------|----------|
| 000    | ZWJoin                         | Done     |
| 001    | ZWCommandResponse              | Not done |
| 002    | ZWSpawnNpc                     | Not done |
| 003    | ZWRemoveNpc                    | Not done |
| 004    | -                              | -        |
| 005    | ZWSaveInstanceSpawner          | Not done |
| 006    | ZWNpcSaid                      | Not done |
| 007    | ZWRemoveHouse                  | Not done |
| 008    | -                              | -        |
| 009    | ZwSkillControllerState         | Not done |
| 00A    | ZWCreateBuff                   | Not done |
| 00B    | ZWRemoveBuff                   | Not done |
| 00C    | ZWClearCombat                  | Not done |
| 00D    | ZWMakeAggroTargetHostile       | Not done |
| 00E    | -                              | -        |
| 00F    | ZWUnitModelPostureChanged      | Not done |
| 010    | ZWUnitFell                     | Not done |
| 011    | ZWErrorMsgToPlayer             | Not done |
| 012    | ZWNpcSpawner                   | Not done |
| 013    | ZWNPCInteractionEnded          | Not done |
| 014    | -                              | -        |
| 015    | -                              | -        |
| 016    | ZWChangeTarget                 | Not done |
| 017    | ZWStartSkill                   | Not done |
| 018    | ZWStopSkill                    | Not done |
| 019    | ZWStopCasting                  | Not done |
| 01A    | ZWTimeOfDay                    | Not done |
| 01B    | ZWDetailedTimeOfDay            | Not done |
| 01C    | ZWZoneLoaded                   | Not done |
| 01D    | ZWSandboxOnlineHeightmapAction | Not done |
| 01E    | -                              | -        |
| 01F    | ZWSandboxOnlinePlayerPos       | Not done |
| 020    | ZWEnterArea                    | Not done |
| 021    | ZWLeaveArea                    | Not done |
| 022    | ZWGimmickMovement              | Not done |
| 023    | ZWRequestSpawnStaticGimmick    | Not done |
| 024    | ZWGimmickJointBroken           | Not done |
| 025    | ZWGimmickResetJoints           | Not done |
| 026    | ZWGimmickRemoveParts           | Not done |
| 027    | ZWGimmickCollided              | Not done |
| 028    | -                              | -        |
| 029    | ZWNpcInteractionStatusChanged  | Not done |
| 02A    | ZWResponseCombatUnits          | Not done |
| 02B    | ZWAggroTargetChanged           | Not done |
| 02C    | ZWReportZoneStatus             | Not done |
| 02D    | ZWRayCastingResult             | Not done |
| 02E    | ZWKillNpc                      | Not done |
| 02F    | ZWTowerDefReportPlayability    | Not done |
| 030    | ZWAiDebug                      | Not done |
| 031    | ZWAiAggro                      | Not done |
| 032    | ZWUnitLocation                 | Not done |
| 032    | ZWUnitMovementsBuffer          | Not done |
| 033    | ZWBackpackDropped              | Not done |
| 034    | ZWReportMoleMiner              | Not done |
| 035    | ZWReportMoleTrader             | Not done |
| 036    | ZWReportMoveHack               | Not done |
| 037    | ZWDoodadCollideUnit            | Not done |
| 038    | ZWHeartbeat                    | Not done |
| 039    | ZWRegisterNpcAbuser            | Not done |
| 03A    | ZWUnregisterNpcAbusers         | Not done |
| 03B    | ZWClearNpcAbusers              | Not done |
| 03C    | ZWDumpNpc                      | Not done |

### WorldZone

| Opcode | Name                           | Status   |
|--------|--------------------------------|----------|
| 000    | WZJoinResponse                 | Done     |
| 001    | WZRunCommand                   | Not done |
| 002    | WZNpcState                     | Not done |
| 003    | WZNpcSpawnFailed               | Not done |
| 004    | WZNpcStartDespawn              | Not done |
| 005    | WZSpawnerList                  | Not done |
| 006    | WZHouseState                   | Not done |
| 007    | WZUnitState                    | Not done |
| 008    | WZUnitRemoved                  | Not done |
| 009    | WZUnitMovement                 | Not done |
| 00A    | WZUnitAttached                 | Not done |
| 00B    | WZUnitDetached                 | Not done |
| 00C    | WZUnitHung                     | Not done |
| 00D    | WZUnitUnhung                   | Not done |
| 00E    | WZSkillControllerState         | Not done |
| 00F    | WZCreateSkillController        | Not done |
| 010    | WZUnitModelPostureChanged      | Not done |
| 011    | WZFactionList                  | Not done |
| 012    | WZFactionRelationList          | Not done |
| 013    | WZFactionCreated               | Not done |
| 014    | WZFactionSponsorChanged        | Not done |
| 015    | WZFactionDismissed             | Not done |
| 016    | WZFactionSetRelationState      | Not done |
| 017    | WZUnitFactionChanged           | Not done |
| 018    | WZUnitExpeditionChanged        | Not done |
| 019    | WZFactionIndependence          | Not done |
| 01A    | -                              | -        |
| 01B    | WZUnitEquipmentChanged         | Not done |
| 01C    | WZUnitPoints                   | Not done |
| 01D    | WZUnitDeath                    | Not done |
| 01E    | WZUnitResurrection             | Not done |
| 01F    | WZForceAttackSet               | Not done |
| 020    | WZCombatEngaged                | Not done |
| 021    | WZCombatCleared                | Not done |
| 022    | WZCvFCombatRelationship        | Not done |
| 023    | WZFvFCombatRelationship        | Not done |
| 024    | WZUnitDuelState                | Not done |
| 025    | WZTargetChanged                | Not done |
| 026    | WZSkillStarted                 | Not done |
| 027    | WZSkillFired                   | Not done |
| 028    | WZSkillEnded                   | Not done |
| 029    | WZSkillStopped                 | Not done |
| 02A    | WZCastingStopped               | Not done |
| 02B    | WZUnitDamaged                  | Not done |
| 02C    | WZUnitHealed                   | Not done |
| 02D    | WZKnockBackUnit                | Not done |
| 02E    | WZUnitInvisible                | Not done |
| 02F    | -                              | -        |
| 030    | WZSkillCooldownReset           | Not done |
| 031    | WZPlotEvent                    | Not done |
| 032    | WZPlotEnded                    | Not done |
| 033    | WZPlotCastingStopped           | Not done |
| 034    | WZPlotChannelingStopped        | Not done |
| 035    | WZBuffCreated                  | Not done |
| 036    | WZBuffRemoved                  | Not done |
| 037    | WZBuffUpdated                  | Not done |
| 038    | -                              | -        |
| 039    | -                              | -        |
| 03A    | WZUpdateNpcSpawner             | Not done |
| 03B    | WZRequestNpcSpawnerList        | Not done |
| 03C    | WZAddNpcToSpawner              | Not done |
| 03D    | WZActivateNpcSpawnersInArea    | Not done |
| 03E    | -                              | -        |
| 03F    | WZUpdateAggro                  | Not done |
| 040    | WZFakeDeath                    | Not done |
| 041    | WZInteractNPC                  | Not done |
| 042    | WZInteractNPCEnd               | Not done |
| 043    | WZDbNpcCount                   | Not done |
| 044    | WZDbNpcList                    | Not done |
| 045    | WZSlaveMasterChanged           | Not done |
| 046    | WZShipControlChange            | Not done |
| 047    | WZHouseBuildDone               | Not done |
| 048    | WZHouseBuildProgress           | Not done |
| 049    | WZGmCommand                    | Not done |
| 04A    | WZAttackOnQuest                | Not done |
| 04B    | WZFollowUnitOnQuest            | Not done |
| 04C    | WZFollowPathOnQuest            | Not done |
| 04D    | WZRunCommandSetOnQuest         | Not done |
| 04E    | WZTimeOfDay                    | Not done |
| 04F    | WZDetailedTimeOfDay            | Not done |
| 050    | WZSandboxOnlineHeightmapAction | Not done |
| 051    | WZSandboxOnlineUndo            | Not done |
| 052    | WZSandboxOnlinePlayerPos       | Not done |
| 053    | WZGimmickReloadStatics         | Not done |
| 054    | WZGimmickCreated               | Not done |
| 055    | WZGimmickRemoved               | Not done |
| 056    | WZGimmickMovement              | Not done |
| 057    | WZGimmickGrasped               | Not done |
| 058    | WZGimmickSetMovement           | Not done |
| 059    | WZPhysicalExplosion            | Not done |
| 05A    | WZDominionData                 | Not done |
| 05B    | WZDominionDeleted              | Not done |
| 05C    | WZSiegeState                   | Not done |
| 05D    | WZSiegeMember                  | Not done |
| 05E    | WZTowerDefReload               | Not done |
| 05F    | WZTowerDefQueryPlayability     | Not done |
| 060    | WZTowerDefStart                | Not done |
| 061    | WZTowerDefEnd                  | Not done |
| 062    | WZTowerDefWaveStart            | Not done |
| 063    | WZNpcControl                   | Not done |
| 064    | WZNpcSpawnerEvent              | Not done |
| 065    | WZRequestCombatUnits           | Not done |
| 066    | WZCreateDoodad                 | Not done |
| 067    | WZRemoveDoodad                 | Not done |
| 068    | WZDoodadChangePhase            | Not done |
| 069    | WZVegetationCutdown            | Not done |
| 06A    | WZRequestZoneStatus            | Not done |
| 06B    | WZPhysicsCPU                   | Not done |
| 06C    | -                              | -        |
| 06D    | WZRayCasting                   | Not done |
| 06E    | WZAiDebug                      | Not done |
| 06F    | WZBuffLearned                  | Not done |
| 070    | WZSkillsReset                  | Not done |
| 071    | WZLevelChanged                 | Not done |
| 072    | WZDropBackpack                 | Not done |
| 073    | WZCheckMoleMiner               | Not done |
| 074    | WZCheckMoleTrader              | Not done |