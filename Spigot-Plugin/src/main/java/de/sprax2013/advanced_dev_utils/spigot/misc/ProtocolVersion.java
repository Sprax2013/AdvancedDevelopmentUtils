package de.sprax2013.advanced_dev_utils.spigot.misc;

public enum ProtocolVersion {
	v19w34a(550, "19w34a"), v1_14_4(498, "1.14.4"), v1_14_4_pre7(497, "1.14.4-pre7"), v1_14_4_pre6(496, "1.14.4-pre6"),
	v1_14_4_pre5(495, "1.14.4-pre5"), v1_14_4_pre4(494, "1.14.4-pre4"), v1_14_4_pre3(493, "1.14.4-pre3"),
	v1_14_4_pre2(492, "1.14.4-pre2"), v1_14_4_pre1(491, "1.14.4-pre1"), v1_14_3(490, "1.14.3"),
	v1_14_3_pre4(489, "1.14.3-pre4"), v1_14_3_pre3(488, "1.14.3-pre3"), v1_14_3_pre2(487, "1.14.3-pre2"),
	v1_14_3_pre1(486, "1.14.3-pre1"), v1_14_2(485, "1.14.2"), v1_14_1(480, "1.14.1"), v1_14(477, "1.14"),
	v19w14b(471, "19w14b"), v19w14a(470, "19w14a"), v19w13b(469, "19w13b"), v19w13a(468, "19w13a"),
	v19w12b(467, "19w12b"), v19w12a(466, "19w12a"), v19w11b(465, "19w11b"), v19w11a(464, "19w11a"),
	v19w09a(463, "19w09a"), v19w08b(462, "19w08b"), v19w08a(461, "19w08a"), v19w07a(460, "19w07a"),
	v19w06a(459, "19w06a"), v19w05a(458, "19w05a"), v19w04b(457, "19w04b"), v19w04a(456, "19w04a"),
	v19w03c(455, "19w03c"), v19w03b(454, "19w03b"), v19w03a(453, "19w03a"), v19w02a(452, "19w02a"),
	v18w50a(451, "18w50a"), v18w49a(450, "18w49a"), v18w48b(449, "18w48b"), v18w48a(448, "18w48a"),
	v18w47b(447, "18w47b"), v18w47a(446, "18w47a"), v18w46a(445, "18w46a"), v18w45a(444, "18w45a"),
	v18w44a(443, "18w44a"), v18w43c(442, "18w43c"), v18w43b(441, "18w43b"), v18w43a(441, "18w43a"),
	v1_13_2(404, "1.13.2"), v1_13_2_pre2(403, "1.13.2-pre2"), v1_13_2_pre1(402, "1.13.2-pre1"), v1_13_1(401, "1.13.1"),
	v1_13_1_pre2(400, "1.13.1-pre2"), v1_13_1_pre1(399, "1.13.1-pre1"), v18w33a(398, "18w33a"), v18w32a(397, "18w32a"),
	v18w31a(396, "18w31a"), v18w30b(395, "18w30b"), v18w30a(394, "18w30a"), v1_13(393, "1.13"),
	v1_13_pre10(392, "1.13-pre10"), v1_13_pre9(391, "1.13-pre9"), v1_13_pre8(390, "1.13-pre8"),
	v1_13_pre7(389, "1.13-pre7"), v1_13_pre6(388, "1.13-pre6"), v1_13_pre5(387, "1.13-pre5"),
	v1_13_pre4(386, "1.13-pre4"), v1_13_pre3(385, "1.13-pre3"), v1_13_pre2(384, "1.13-pre2"),
	v1_13_pre1(383, "1.13-pre1"), v18w22c(382, "18w22c"), v18w22b(381, "18w22b"), v18w22a(380, "18w22a"),
	v18w21b(379, "18w21b"), v18w21a(378, "18w21a"), v18w20c(377, "18w20c"), v18w20b(376, "18w20b"),
	v18w20a(375, "18w20a"), v18w19b(374, "18w19b"), v18w19a(373, "18w19a"), v18w16a(372, "18w16a"),
	v18w15a(371, "18w15a"), v18w14b(370, "18w14b"), v18w14a(369, "18w14a"), v18w11a(368, "18w11a"),
	v18w10d(367, "18w10d"), v18w10c(366, "18w10c"), v18w10b(365, "18w10b"), v18w10a(364, "18w10a"),
	v18w09a(363, "18w09a"), v18w08b(362, "18w08b"), v18w08a(361, "18w08a"), v18w07c(360, "18w07c"),
	v18w07b(359, "18w07b"), v18w07a(358, "18w07a"), v18w06a(357, "18w06a"), v18w05a(356, "18w05a"),
	v18w03b(355, "18w03b"), v18w03a(354, "18w03a"), v18w02a(353, "18w02a"), v18w01a(352, "18w01a"),
	v17w50a(351, "17w50a"), v17w49b(350, "17w49b"), v17w49a(349, "17w49a"), v17w48a(348, "17w48a"),
	v17w47b(347, "17w47b"), v17w47a(346, "17w47a"), v17w46a(345, "17w46a"), v17w45b(344, "17w45b"),
	v17w45a(343, "17w45a"), v17w43b(342, "17w43b"), v17w43a(341, "17w43a"), v1_12_2(340, "1.12.2"),
	v1_12_2_pre2(339, "1.12.2-pre1, 1.12.2-pre2"), v1_12_1(338, "1.12.1"), v1_12_1_pre1(337, "1.12.1-pre1"),
	v17w31a(336, "17w31a"), v1_12(335, "1.12"), v1_12_pre7(334, "1.12-pre7"), v1_12_pre6(333, "1.12-pre6"),
	v1_12_pre5(332, "1.12-pre5"), v1_12_pre4(331, "1.12-pre4"), v1_12_pre3(330, "1.12-pre3"),
	v1_12_pre2(329, "1.12-pre2"), v1_12_pre1(328, "1.12-pre1"), v17w18b(327, "17w18b"), v17w18a(326, "17w18a"),
	v17w17b(325, "17w17b"), v17w17a(324, "17w17a"), v17w16b(323, "17w16b"), v17w16a(322, "17w16a"),
	v17w15a(321, "17w15a"), v17w14a(320, "17w14a"), v17w13b(319, "17w13b"), v17w13a(318, "17w13a"),
	v17w06a(317, "17w06a"), v1_11_2(316, "1.11.2"), v1_11(315, "1.11"), v1_11_pre1(314, "1.11-pre1"),
	v16w44a(313, "16w44a"), v16w42a(312, "16w42a"), v16w41a(311, "16w41a"), v16w40a(310, "16w40a"),
	v16w39c(309, "16w39c"), v16w39b(308, "16w39b"), v16w39a(307, "16w39a"), v16w38a(306, "16w38a"),
	v16w36a(305, "16w36a"), v16w35a(304, "16w35a"), v16w33a(303, "16w33a"), v16w32b(302, "16w32b"),
	v16w32a(301, "16w32a"), v1_10_2(210, "1.10.2"), v1_10_pre2(205, "1.10-pre2"), v1_10_pre1(204, "1.10-pre1"),
	v16w21b(203, "16w21b"), v16w21a(202, "16w21a"), v16w20a(201, "16w20a"), v1_9_4(110, "1.9.4"),
	v1_9_3_pre1(109, "1.9.3-pre1"), v1_RV_Pre1(108, "1.RV-Pre1"), v1_9_1(108, "1.9.1"), v1_9_1_pre1(107, "1.9.1-pre1"),
	v1_9_pre4(106, "1.9-pre4"), v1_9_pre3(105, "1.9-pre3"), v1_9_pre2(104, "1.9-pre2"), v1_9_pre1(103, "1.9-pre1"),
	v16w07b(102, "16w07b"), v16w07a(101, "16w07a"), v16w06a(100, "16w06a"), v16w05b(99, "16w05b"),
	v16w05a(98, "16w05a"), v16w04a(97, "16w04a"), v16w03a(96, "16w03a"), v16w02a(95, "16w02a"), v15w51b(94, "15w51b"),
	v15w51a(93, "15w51a"), v15w50a(92, "15w50a"), v15w49b(91, "15w49b"), v15w49a(90, "15w49a"), v15w47c(89, "15w47c"),
	v15w47b(88, "15w47b"), v15w47a(87, "15w47a"), v15w46a(86, "15w46a"), v15w45a(85, "15w45a"), v15w44b(84, "15w44b"),
	v15w44a(83, "15w44a"), v15w43c(82, "15w43c"), v15w43b(81, "15w43b"), v15w43a(80, "15w43a"), v15w42a(79, "15w42a"),
	v15w41b(78, "15w41b"), v15w41a(77, "15w41a"), v15w40b(76, "15w40b"), v15w40a(75, "15w40a"), v15w39c(74, "15w39c"),
	v15w38b(73, "15w38b"), v15w38a(72, "15w38a"), v15w37a(71, "15w37a"), v15w36d(70, "15w36d"), v15w36c(69, "15w36c"),
	v15w36b(68, "15w36b"), v15w36a(67, "15w36a"), v15w35e(66, "15w35e"), v15w35d(65, "15w35d"), v15w35c(64, "15w35c"),
	v15w35b(63, "15w35b"), v15w35a(62, "15w35a"), v15w34d(61, "15w34d"), v15w34c(60, "15w34c"), v15w34b(59, "15w34b"),
	v15w34a(58, "15w34a"), v15w33c(57, "15w33c"), v15w33b(56, "15w33b"), v15w33a(55, "15w33a"), v15w32c(54, "15w32c"),
	v15w32b(53, "15w32b"), v15w32a(52, "15w32a"), v15w31c(51, "15w31c"), v15w31b(50, "15w31b"), v15w31a(49, "15w31a"),
	v15w14a(48, "15w14a"), v1_8_9(47, "1.8"), v1_8_pre3(46, "1.8-pre3"), v1_8_pre2(45, "1.8-pre2"),
	v1_8_pre1(44, "1.8-pre1"), v14w34d(43, "14w34d"), v14w34c(42, "14w34c"), v14w34b(41, "14w34b"),
	v14w34a(40, "14w34a"), v14w33c(39, "14w33c"), v14w33b(38, "14w33b"), v14w33a(37, "14w33a"), v14w32d(36, "14w32d"),
	v14w32c(35, "14w32c"), v14w32b(34, "14w32b"), v14w32a(33, "14w32a"), v14w31a(32, "14w31a"), v14w30c(31, "14w30c"),
	v14w30b(30, "14w30b"), v14w29a(29, "14w29a"), v14w28b(28, "14w28b"), v14w28a(27, "14w28a"), v14w27b(26, "14w27b"),
	v14w26c(25, "14w26c"), v14w26b(24, "14w26b"), v14w26a(23, "14w26a"), v14w25b(22, "14w25b"), v14w25a(21, "14w25a"),
	v14w21b(20, "14w21b"), v14w21a(19, "14w21a"), v14w20b(18, "14w20b"), v14w19a(17, "14w19a"), v14w18b(16, "14w18b"),
	v14w17a(15, "14w17a"), v14w11b(14, "14w11b"), v14w10c(13, "14w10c"), v14w08a(12, "14w08a"), v14w07a(11, "14w07a"),
	v14w06b(10, "14w06b"), v14w05b(9, "14w05b"), v14w04b(8, "14w04b"), v14w04a(7, "14w04a"), v14w03b(6, "14w03b"),
	v14w02c(5, "14w02c"), v1_7_5(4, "1.7.5"), v1_7_1_pre(3, "1.7.1-pre"), v13w43a(2, "13w43a"), v13w42b(1, "13w42b"),
	v13w41b(0, "13w41b"), v13w41a(0, "13w41a");

//	v1_14_3(490, "1.14.3"), v1_14_2(485, "1.14.2"), v1_14_1(480, "1.14.1"), v1_14(477, "1.14"), v1_13_2(404, "1.13.2"),
//	v1_13_1(401, "1.13.1"), v1_13(393, "1.13"), v1_12_2(340, "1.12.2"), v1_12_1(338, "1.12.1"), v1_12(335, "1.12"),
//	v1_11_2(316, "1.11.1 / 1.11.2"), v1_11_1(v1_11_2), v1_11(315, "1.11"), v1_10_X(210, "1.10.X"),
//	v1_9_4(110, "1.9.3 / 1.9.4"), v1_9_3(v1_9_4), v1_9_2(109, "1.9.2"), v1_9_1(108, "1.9.1"), v1_9(107, "1.9"),
//	v1_8(47, "1.8.X");

	private final int version;
	private final String[] names;

	ProtocolVersion(int version, String... names) {
		this.version = version;
		this.names = names;
	}

	public static ProtocolVersion getByVersion(int version) {
		for (ProtocolVersion pVer : values()) {
			if (pVer.version == version) {
				return pVer;
			}
		}

		return null;
	}

	public static ProtocolVersion getByName(String name) {
		return null;
	}

//	public static String getDisplayname(int version) {
//		ProtocolVersion pVer = getByVersion(version);
//
//		return pVer == null ? String.valueOf(version) : pVer.name;
//	}

	public static void main(String[] args) {
		// Copy'n Paste the Text (Table) from
		// https://wiki.vg/Protocol_version_numbers#Versions_after_the_Netty_rewrite and
		// insert it as input-string
		final String input = "19w34a 	550 	page\r\n" + "1.14.4 	498 	page\r\n"
				+ "1.14.4-pre7 	497 	page\r\n" + "1.14.4-pre6 	496 	page\r\n"
				+ "1.14.4-pre5 	495 	page\r\n" + "1.14.4-pre4 	494 	page\r\n"
				+ "1.14.4-pre3 	493 	page\r\n" + "1.14.4-pre2 	492 	page\r\n"
				+ "1.14.4-pre1 	491 	page\r\n" + "1.14.3 	490 	page\r\n"
				+ "1.14.3 - Combat Test 	500 	\r\n" + "1.14.3-pre4 	489 	page\r\n"
				+ "1.14.3-pre3 	488 	page\r\n" + "1.14.3-pre2 	487 	page\r\n"
				+ "1.14.3-pre1 	486 	page\r\n" + "1.14.2 	485 	page\r\n"
				+ "1.14.2 Pre-Release 4 	484 	page\r\n" + "1.14.2 Pre-Release 3 	483 	page\r\n"
				+ "1.14.2 Pre-Release 2 	482 	page\r\n" + "1.14.2 Pre-Release 1 	481 	page\r\n"
				+ "1.14.1 	480 	page\r\n" + "1.14.1 Pre-Release 2 	479 	page\r\n"
				+ "1.14.1 Pre-Release 1 	478 	page\r\n" + "1.14 	477 	page\r\n"
				+ "1.14 Pre-Release 5 	476 	page\r\n" + "1.14 Pre-Release 4 	475 	page\r\n"
				+ "1.14 Pre-Release 3 	474 	page\r\n" + "1.14 Pre-Release 2 	473 	page\r\n"
				+ "1.14 Pre-Release 1 	472 	page\r\n" + "19w14b 	471 	page\r\n"
				+ "19w14a 	470 	page\r\n" + "19w13b 	469 	page\r\n" + "19w13a 	468 	page\r\n"
				+ "19w12b 	467 	page\r\n" + "19w12a 	466 	page\r\n" + "19w11b 	465 	page\r\n"
				+ "19w11a 	464 	page\r\n" + "19w09a 	463 	page\r\n" + "19w08b 	462 	page\r\n"
				+ "19w08a 	461 	page\r\n" + "19w07a 	460 	page\r\n" + "19w06a 	459 	page\r\n"
				+ "19w05a 	458 	page\r\n" + "19w04b 	457 	page\r\n" + "19w04a 	456 	page\r\n"
				+ "19w03c 	455 	page\r\n" + "19w03b 	454 	page\r\n" + "19w03a 	453 	page\r\n"
				+ "19w02a 	452 	page\r\n" + "18w50a 	451 	page\r\n" + "18w49a 	450 	page\r\n"
				+ "18w48b 	449 	page\r\n" + "18w48a 	448 	page\r\n" + "18w47b 	447 	page\r\n"
				+ "18w47a 	446 	page\r\n" + "18w46a 	445 	page\r\n" + "18w45a 	444 	page\r\n"
				+ "18w44a 	443 	page\r\n" + "18w43c 	442 	page\r\n" + "18w43b 	441 	page\r\n"
				+ "18w43a 	441 	\r\n" + "1.13.2 	404 	page (Plugin channels)\r\n"
				+ "1.13.2-pre2 	403 	page\r\n" + "1.13.2-pre1 	402 	page\r\n" + "1.13.1 	401 	page\r\n"
				+ "1.13.1-pre2 	400 	page\r\n" + "1.13.1-pre1 	399 	page\r\n" + "18w33a 	398 	page\r\n"
				+ "18w32a 	397 	page\r\n" + "18w31a 	396 	page\r\n" + "18w30b 	395 	page\r\n"
				+ "18w30a 	394 	page\r\n" + "1.13 	393 	page\r\n" + "1.13-pre10 	392 	page\r\n"
				+ "1.13-pre9 	391 	page\r\n" + "1.13-pre8 	390 	page\r\n" + "1.13-pre7 	389 	page\r\n"
				+ "1.13-pre6 	388 	page\r\n" + "1.13-pre5 	387 	page\r\n" + "1.13-pre4 	386 	page\r\n"
				+ "1.13-pre3 	385 	page\r\n" + "1.13-pre2 	384 	page\r\n" + "1.13-pre1 	383 	page\r\n"
				+ "18w22c 	382 	page\r\n" + "18w22b 	381 	page\r\n" + "18w22a 	380 	page\r\n"
				+ "18w21b 	379 	page\r\n" + "18w21a 	378 	page\r\n" + "18w20c 	377 	page\r\n"
				+ "18w20b 	376 	page\r\n" + "18w20a 	375 	page\r\n" + "18w19b 	374 	page\r\n"
				+ "18w19a 	373 	page\r\n" + "18w16a 	372 	page\r\n" + "18w15a 	371 	page\r\n"
				+ "18w14b 	370 	page\r\n" + "18w14a 	369 	page\r\n" + "18w11a 	368 	page\r\n"
				+ "18w10d 	367 	page\r\n" + "18w10c 	366 	page\r\n" + "18w10b 	365 	page\r\n"
				+ "18w10a 	364 	page\r\n" + "18w09a 	363 	page\r\n" + "18w08b 	362 	page\r\n"
				+ "18w08a 	361 	page\r\n" + "18w07c 	360 	page\r\n" + "18w07b 	359 	page\r\n"
				+ "18w07a 	358 	page\r\n" + "18w06a 	357 	page\r\n" + "18w05a 	356 	page\r\n"
				+ "18w03b 	355 	page\r\n" + "18w03a 	354 	\r\n" + "18w02a 	353 	page\r\n"
				+ "18w01a 	352 	page\r\n" + "17w50a 	351 	page\r\n" + "17w49b 	350 	page\r\n"
				+ "17w49a 	349 	page\r\n" + "17w48a 	348 	page\r\n" + "17w47b 	347 	page\r\n"
				+ "17w47a 	346 	page\r\n" + "17w46a 	345 	page\r\n" + "17w45b 	344 	page\r\n"
				+ "17w45a 	343 	page\r\n" + "17w43b 	342 	page\r\n" + "17w43a 	341 	page\r\n"
				+ "1.12.2 	340 	page\r\n" + "\r\n" + "Warning.png Hover for warning\r\n" + "\r\n"
				+ "    Data types\r\n" + "    Slot Data\r\n" + "    Chunk Format\r\n" + "    Entity metadata\r\n"
				+ "    Entity statuses\r\n" + "    Object Data\r\n" + "    Block Actions\r\n"
				+ "    Plugin channels\r\n" + "\r\n" + "1.12.2-pre2 	339 	page\r\n" + "1.12.2-pre1\r\n"
				+ "1.12.1 	338 	page\r\n" + "1.12.1-pre1 	337 	page\r\n" + "17w31a 	336 	page\r\n"
				+ "1.12 	335 	page\r\n" + "1.12-pre7 	334 	page\r\n" + "1.12-pre6 	333 	page\r\n"
				+ "1.12-pre5 	332 	page\r\n" + "1.12-pre4 	331 	page\r\n" + "1.12-pre3 	330 	page\r\n"
				+ "1.12-pre2 	329 	page\r\n" + "1.12-pre1 	328 	page\r\n" + "17w18b 	327 	page\r\n"
				+ "17w18a 	326 	page\r\n" + "17w17b 	325 	page\r\n" + "17w17a 	324 	page\r\n"
				+ "17w16b 	323 	page\r\n" + "17w16a 	322 	page\r\n" + "17w15a 	321 	page\r\n"
				+ "17w14a 	320 	page\r\n" + "17w13b 	319 	page\r\n" + "17w13a 	318 	page\r\n"
				+ "17w06a 	317 	page\r\n" + "1.11.2 	316 	page\r\n" + "1.11.1\r\n" + "16w50a\r\n"
				+ "1.11 	315 	page\r\n" + "1.11-pre1 	314 	page\r\n" + "16w44a 	313 	page\r\n"
				+ "16w43a\r\n" + "16w42a 	312 	page\r\n" + "16w41a 	311 	page\r\n"
				+ "16w40a 	310 	page\r\n" + "16w39c 	309 	page\r\n" + "16w39b 	308 	page\r\n"
				+ "16w39a 	307 	page\r\n" + "16w38a 	306 	page\r\n" + "16w36a 	305 	page\r\n"
				+ "16w35a 	304 	page\r\n" + "16w33a 	303 	page\r\n" + "16w32b 	302 	page\r\n"
				+ "16w32a 	301 	page\r\n" + "1.10.2 	210 	page\r\n" + "1.10.1\r\n" + "1.10\r\n"
				+ "1.10-pre2 	205 	page\r\n" + "1.10-pre1 	204 	page\r\n" + "16w21b 	203 	page\r\n"
				+ "16w21a 	202 	page\r\n" + "16w20a 	201 	page\r\n" + "1.9.4 	110 	page\r\n" + "1.9.3\r\n"
				+ "1.9.3-pre3\r\n" + "1.9.3-pre2\r\n" + "1.9.3-pre1 	109 	page\r\n" + "16w15b\r\n" + "16w15a\r\n"
				+ "16w14a\r\n" + "1.9.2\r\n" + "1.RV-Pre1 	108 	[note 1]\r\n" + "1.9.1 	108 	page\r\n"
				+ "1.9.1-pre3\r\n" + "1.9.1-pre2\r\n" + "1.9.1-pre1 	107 	page\r\n" + "1.9\r\n"
				+ "1.9-pre4 	106 	page\r\n" + "1.9-pre3 	105 	\r\n" + "1.9-pre2 	104 	\r\n"
				+ "1.9-pre1 	103 	\r\n" + "16w07b 	102 	\r\n" + "16w07a 	101 	\r\n"
				+ "16w06a 	100 	\r\n" + "16w05b 	99 	\r\n" + "16w05a 	98 	\r\n" + "16w04a 	97 	\r\n"
				+ "16w03a 	96 	\r\n" + "16w02a 	95 	page\r\n" + "15w51b 	94 	page\r\n" + "15w51a 	93 	\r\n"
				+ "15w50a 	92 	\r\n" + "15w49b 	91 	\r\n" + "15w49a 	90 	\r\n" + "15w47c 	89 	\r\n"
				+ "15w47b 	88 	\r\n" + "15w47a 	87 	\r\n" + "15w46a 	86 	\r\n" + "15w45a 	85 	\r\n"
				+ "15w44b 	84 	\r\n" + "15w44a 	83 	\r\n" + "15w43c 	82 	\r\n" + "15w43b 	81 	\r\n"
				+ "15w43a 	80 	\r\n" + "15w42a 	79 	\r\n" + "15w41b 	78 	\r\n" + "15w41a 	77 	\r\n"
				+ "15w40b 	76 	page\r\n" + "15w40a 	75 	\r\n" + "15w39c 	74 	\r\n" + "15w39b\r\n" + "15w39a\r\n"
				+ "15w38b 	73 	page\r\n" + "15w38a 	72 	page\r\n" + "15w37a 	71 	\r\n"
				+ "15w36d 	70 	page\r\n" + "15w36c 	69 	page\r\n" + "15w36b 	68 	\r\n" + "15w36a 	67 	\r\n"
				+ "15w35e 	66 	page\r\n" + "15w35d 	65 	\r\n" + "15w35c 	64 	\r\n" + "15w35b 	63 	page\r\n"
				+ "15w35a 	62 	\r\n" + "15w34d 	61 	\r\n" + "15w34c 	60 	\r\n" + "15w34b 	59 	\r\n"
				+ "15w34a 	58 	page\r\n" + "15w33c 	57 	page\r\n" + "15w33b 	56 	page\r\n"
				+ "15w33a 	55 	page\r\n" + "15w32c 	54 	page\r\n" + "15w32b 	53 	\r\n"
				+ "15w32a 	52 	page\r\n" + "15w31c 	51 	page\r\n" + "15w31b 	50 	page[note 2]\r\n"
				+ "15w31a 	49 	\r\n" + "15w14a 	48 	\r\n" + "1.8.9 	47 	page\r\n" + "1.8.8\r\n" + "1.8.7\r\n"
				+ "1.8.6\r\n" + "1.8.5\r\n" + "1.8.4\r\n" + "1.8.3\r\n" + "1.8.2\r\n" + "1.8.2-pre7\r\n"
				+ "1.8.2-pre6\r\n" + "1.8.2-pre5\r\n" + "1.8.2-pre4\r\n" + "1.8.2-pre3\r\n" + "1.8.2-pre2\r\n"
				+ "1.8.2-pre1\r\n" + "1.8.1\r\n" + "1.8.1-pre5\r\n" + "1.8.1-pre4\r\n" + "1.8.1-pre3\r\n"
				+ "1.8.1-pre2\r\n" + "1.8.1-pre1\r\n" + "1.8\r\n" + "1.8-pre3 	46 	\r\n" + "1.8-pre2 	45 	\r\n"
				+ "1.8-pre1 	44 	\r\n" + "14w34d 	43 	\r\n" + "14w34c 	42 	\r\n" + "14w34b 	41 	\r\n"
				+ "14w34a 	40 	\r\n" + "14w33c 	39 	\r\n" + "14w33b 	38 	\r\n" + "14w33a 	37 	\r\n"
				+ "14w32d 	36 	\r\n" + "14w32c 	35 	\r\n" + "14w32b 	34 	\r\n" + "14w32a 	33 	\r\n"
				+ "14w31a 	32 	\r\n" + "14w30c 	31 	\r\n" + "14w30b 	30 	\r\n" + "14w30a\r\n"
				+ "14w29a 	29 	\r\n" + "14w29a\r\n" + "14w28b 	28 	\r\n" + "14w28a 	27 	\r\n"
				+ "14w27b 	26 	\r\n" + "14w27a\r\n" + "14w26c 	25 	\r\n" + "14w26b 	24 	\r\n"
				+ "14w26a 	23 	\r\n" + "14w25b 	22 	\r\n" + "14w25a 	21 	\r\n" + "14w21b 	20 	\r\n"
				+ "14w21a 	19 	\r\n" + "14w20b 	18 	\r\n" + "14w20a\r\n" + "14w19a 	17 	\r\n"
				+ "14w18b 	16 	\r\n" + "14w18a\r\n" + "14w17a 	15 	\r\n" + "14w11b 	14 	\r\n" + "14w11a\r\n"
				+ "14w10c 	13 	\r\n" + "14w10b\r\n" + "14w10a\r\n" + "14w08a 	12 	\r\n" + "14w07a 	11 	\r\n"
				+ "14w06b 	10 	\r\n" + "14w06a\r\n" + "14w05b 	9 	\r\n" + "14w05a\r\n" + "14w04b 	8 	\r\n"
				+ "14w04a 	7 	\r\n" + "14w03b 	6 	\r\n" + "14w03a\r\n" + "14w02c 	5 	page\r\n" + "14w02b\r\n"
				+ "14w02a\r\n" + "1.7.10\r\n" + "1.7.10-pre4\r\n" + "1.7.10-pre3\r\n" + "1.7.10-pre2\r\n"
				+ "1.7.10-pre1\r\n" + "1.7.9\r\n" + "1.7.8\r\n" + "1.7.7\r\n" + "1.7.6\r\n" + "1.7.6-pre2\r\n"
				+ "1.7.6-pre1\r\n" + "1.7.5 	4 	page\r\n" + "1.7.4\r\n" + "1.7.3-pre\r\n" + "13w49a\r\n"
				+ "13w48b\r\n" + "13w48a\r\n" + "13w47e\r\n" + "13w47d\r\n" + "13w47c\r\n" + "13w47b\r\n" + "13w47a\r\n"
				+ "1.7.2\r\n" + "1.7.1-pre 	3 	\r\n" + "1.7-pre\r\n" + "13w43a 	2 	\r\n"
				+ "13w42b 	1 	page\r\n" + "13w42a\r\n" + "13w41b 	0 	page[note 3]\r\n" + "13w41a 	0 	page";

		for (String line : input.split("\r\n")) {
			String[] argArr = line.split(" ");

			if (argArr.length >= 2) {
				String name = argArr[0].trim(), versionStr = argArr[1].trim();

				try {
					// v1_14_3(47, ""),

					System.out.print("v" + name.replace('.', '_').replace('-', '_') + "(" + Integer.parseInt(versionStr)
							+ ", \"" + name + "\"),");
				} catch (@SuppressWarnings("unused") NumberFormatException ex) {
				}
			}
		}
	}
}